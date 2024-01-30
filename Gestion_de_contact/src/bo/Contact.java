package bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String tel1;
    private String tel2;
    private String adresse;
    private String emailPerso;
    private String emailPro;
    private String genre;
    public List<String> groupes =new ArrayList<>();

    public Contact(int id, String nom, String prenom, String tel1, String tel2, String adresse, String emailPerso, String emailPro, String genre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.adresse = adresse;
        this.emailPerso = emailPerso;
        this.emailPro = emailPro;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel1() {
        return tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmailPerso() {
        return emailPerso;
    }

    public String getEmailPro() {
        return emailPro;
    }

    public String getGenre() {
        return genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmailPerso(String emailPerso) {
        this.emailPerso = emailPerso;
    }

    public void setEmailPro(String emailPro) {
        this.emailPro = emailPro;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(nom, contact.nom);
    }

    public List<String> getGroupes() {
        return groupes;
    }
}

