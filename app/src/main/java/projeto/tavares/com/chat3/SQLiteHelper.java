package projeto.tavares.com.chat3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TavaresPC on 14/01/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    private static final String name_db = "AppChat.db";
    private static final int version_db = 2;



    public SQLiteHelper(Context ctx) {
        super(ctx, name_db, null, version_db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CONTATOS = "CREATE TABLE contatos (_id integer primary key autoincrement, ";
           SQL_CONTATOS += "id_user integer, contact_user, name_contact varchar(100))";
           db.execSQL(SQL_CONTATOS);

        String SQL_MENSAGENS = "CREATE TABLE mensagens (_id integer primary key autoincrement, ";
        SQL_MENSAGENS += "id_user integer, contact_user, name_contact, mensagem TEXT, data datetime )";
        db.execSQL(SQL_MENSAGENS);
    }

    public void save_contact(String id_user, int contact_user,String name_contact ) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues ctv = new ContentValues();
        ctv.put("id_user", id_user);
        ctv.put("contact_user", contact_user);
        ctv.put("name_contact", name_contact);

        db.insert("contatos", "_id", ctv);
    }

    public void insert_message(int id_user, int contact_user, String mensagem, String data){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues ctv = new ContentValues();
        ctv.put("id_user", id_user);
        ctv.put("contact_user", contact_user);
        ctv.put("mensagem", mensagem);
        ctv.put("data", data);

         db.insert("mensagens", "_id", ctv);
    }



    public List<Chat> getMessages(int contact_user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mensagens WHERE contact_user = ?", new String[]{String.valueOf(contact_user)});

        List<Chat> lista = new ArrayList<Chat>();

        while (cursor.moveToNext()){
            Chat c = new Chat();
            c.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            c.setId_user(cursor.getInt(cursor.getColumnIndex("id_user")));
            c.setContact_user(cursor.getInt(cursor.getColumnIndex("contact_user")));
            c.setMensagem(cursor.getString(cursor.getColumnIndex("mensagem")));
            lista.add(c);


        }

        return lista;
    }


    public List<Contato> getContacts(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM contatos", null);

        List<Contato> lista = new ArrayList<Contato>();

        while (cursor.moveToNext()){
            Contato c = new Contato();
            c.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            c.setId_user(cursor.getInt(cursor.getColumnIndex("id_user")));
            c.setContact_user(cursor.getInt(cursor.getColumnIndex("contact_user")));
            c.setName_contact(cursor.getString(cursor.getColumnIndex("name_contact")));

            lista.add(c);

        }

        return lista;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
