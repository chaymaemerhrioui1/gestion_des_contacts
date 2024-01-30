package bo;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private String nom;
    //private int id;
    private List<Contact> contacts;


    public Groupe(String nom) {
        this.nom = nom;
        //this.id=id;
        this.contacts = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterContact(Contact contact) {
        contacts.add(contact);
    }

    public void supprimerContact(Contact contact) {
        contacts.remove(contact);
    }


}

