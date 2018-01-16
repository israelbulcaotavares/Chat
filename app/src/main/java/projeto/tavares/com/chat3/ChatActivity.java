package projeto.tavares.com.chat3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    List<Chat> lista = null;

    ListView ltwChats = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final Intent it = getIntent();



         ltwChats = (ListView)findViewById(R.id.ltwChats);

         refreshListView();


      // Toast.makeText(getBaseContext(), it.getStringExtra("name_contact"), Toast.LENGTH_SHORT).show();

        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("USER_INFORMATION", Context.MODE_PRIVATE);
                final String id_user = String.valueOf(preferences.getInt("id_usuario",0));

                String URL = "http://192.168.0.29/chat/send_message.php";

                final EditText txtMensagem = (EditText) findViewById(R.id.txtMensagem);
                if(txtMensagem.getText().length() > 0) {

                    SQLiteHelper db = new SQLiteHelper(getBaseContext());
                    db.insert_message(Integer.parseInt(id_user), it.getIntExtra("contact_user", 0), txtMensagem.getText().toString(), "");
                    refreshListView();

                    Ion.with(getBaseContext())
                            .load(URL)
                            .setMultipartParameter("contact_user", String.valueOf(it.getIntExtra("contact_user", 0)))
                            .setMultipartParameter("id_user", id_user)
                            .setMultipartParameter("message", txtMensagem.getText().toString())
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("retorno").getAsString().equals("YES")) {
                                            txtMensagem.setText("");


                                    } else if (result.get("retorno").getAsString().equals("NO")) {


                                    } else if (result.get("retorno").getAsString().equals("CONTACT_NOT_EXIST")) {

                                    }
                                }
                            });
                }
            }
        });

    }

    private void refreshListView(){
        Intent it = getIntent();


        SQLiteHelper db = new SQLiteHelper(getBaseContext());
        this.lista = db.getMessages(it.getIntExtra("contact_user", 0));
        ltwChats.setAdapter(new ChatAdapter(ChatActivity.this, lista));

    }


}
