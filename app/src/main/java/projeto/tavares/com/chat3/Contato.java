package projeto.tavares.com.chat3;

/**
 * Created by TavaresPC on 15/01/2018.
 */

public class Contato {
    private int _id;
    private int id_user;
    private int contact_user;
    private String name_contact;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getContact_user() {
        return contact_user;
    }

    public void setContact_user(int contact_user) {
        this.contact_user = contact_user;
    }

    public String getName_contact() {
        return name_contact;
    }

    public void setName_contact(String name_contact) {
        this.name_contact = name_contact;
    }
}
