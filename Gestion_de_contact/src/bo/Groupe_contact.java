package bo;

public class Groupe_contact {
    private int id;
    private int id_groupe;
    private int id_contact;

    public Groupe_contact(int id, int id_groupe, int id_contact) {
        this.id = id;
        this.id_groupe = id_groupe;
        this.id_contact = id_contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }
}