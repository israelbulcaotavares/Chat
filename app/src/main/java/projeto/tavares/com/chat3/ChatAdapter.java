package projeto.tavares.com.chat3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TavaresPC on 15/01/2018.
 */

public class ChatAdapter extends BaseAdapter {

    private Context ctx = null;
    private List<Chat> lista = null;


    public ChatAdapter(Context ctx, List<Chat> lista) {
        this.ctx = ctx;
        this.lista=lista;


    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Chat getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = null;


        if (convertView == null){
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.model_chats, null);
        }else {

            v = convertView;
        }

        Chat c = getItem(position);

        TextView txvMensagem = (TextView) v.findViewById(R.id.txvMensagem);
        txvMensagem.setText(c.getMensagem());

        LinearLayout containerChat = (LinearLayout) v.findViewById(R.id.containerChat);


        SharedPreferences preferences = ((Activity)ctx).getSharedPreferences("USER_INFORMATION", Context.MODE_PRIVATE);
        final int id_user = preferences.getInt("id_usuario",0);

        if (id_user == c.getId_user()){
            txvMensagem.setBackgroundResource(R.drawable.bubble_green);
            containerChat.setGravity(Gravity.RIGHT);

        }else {
            txvMensagem.setBackgroundResource(R.drawable.bubble_yellow);
            containerChat.setGravity(Gravity.LEFT);
        }


        return v;
    }
}
