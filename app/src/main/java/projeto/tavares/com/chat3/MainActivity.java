package projeto.tavares.com.chat3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showChat();


        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });


        Button BtnLogar = (Button) findViewById(R.id.BtnLogar);


        BtnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txtLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
                final EditText txtLoginSenha = (EditText) findViewById(R.id.txtLoginSenha);


                int error = 0;

                if (txtLoginEmail.getText().toString().equals("")) {
                    txtLoginEmail.setError("Preencha o campo email.");
                    txtLoginEmail.requestFocus();
                    error = 1;
                } else if (txtLoginSenha.getText().toString().equals("")) {
                    txtLoginSenha.setError("Preencha o campo senha.");
                    txtLoginSenha.requestFocus();
                    error = 1;
                }


                if (error == 0) {

                    String URL = "http://192.168.0.29/chat/login_user.php";


                    Ion.with(getBaseContext())
                            .load(URL)
                            .setBodyParameter("email_user", txtLoginEmail.getText().toString())
                            .setBodyParameter("senha_user", txtLoginSenha.getText().toString())
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("retorno").getAsInt() > 0) {
                                        SharedPreferences.Editor preferences = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE).edit();
                                        preferences.putInt("id_usuario", result.get("retorno").getAsInt());
                                        preferences.putString("nome_usuario", result.get("nome_usuario").getAsString());
                                        preferences.putString("email_usuario", result.get("email_usuario").getAsString());
                                        preferences.putString("photo_usuario", result.get("photo_usuario").getAsString());
                                        preferences.commit();


                                        showChat();


                                        Toast.makeText(getBaseContext(), "Logado!", Toast.LENGTH_LONG).show();

                                    } else {

                                        Toast.makeText(getBaseContext(), "Erro", Toast.LENGTH_LONG).show();


                                    }

                                }
                            });

                }

            }
        });


    }

    private void showChat() {
        SharedPreferences preferences = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);
        if (preferences.getInt("id_usuario", 0) > 0) {


            Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
            startActivity(intent);
            finish();


        }


    }
}
