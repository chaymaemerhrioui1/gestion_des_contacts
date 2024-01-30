package data;

import bo.Contact;

import java.sql.*;
import java.util.*;

public class ContactDAO {
    public static final String ANSI_BLUE = "\u001B[96m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestion";

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private Connection con;
    private Statement stn;
    private List<Contact> contacts = new ArrayList<>();

    public ContactDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root", "");
            stn = con.createStatement();
            System.out.println("Bienvenue ,votre connection est bien connecte !!");
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }

    public void ajouterContact(Contact contact) {
        contacts.add(contact);
        try {
            // Créer une instruction SQL pour insérer un nouveau contact dans la table "contact".
            String sql = "INSERT INTO contact (nom, prenom, tel1, tel2, adresse, emailPerso, emailpro, genre) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            // Préparer l'instruction SQL avec les valeurs du contact.
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setString(3, contact.getTel1());
            statement.setString(4, contact.getTel2());
            statement.setString(5, contact.getAdresse());
            statement.setString(6, contact.getEmailPerso());
            statement.setString(7, contact.getEmailPro());
            statement.setString(8, contact.getGenre());

            // Exécuter l'instruction SQL pour insérer le nouveau contact.
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le contact a été ajouté avec succès ds db.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherContacts() {
        try {
            String query = "SELECT * FROM contact ORDER BY nom";
            ResultSet rs = stn.executeQuery(query);
            List<Contact> contacts = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String telephone1 = rs.getString("tel1");
                String telephone2 = rs.getString("tel2");
                String adresse = rs.getString("adresse");
                String emailPersonnel = rs.getString("emailPerso");
                String emailprofessionnel = rs.getString("emailpro");
                String genre = rs.getString("genre");
                Contact contact = new Contact(id, nom, prenom, telephone1, telephone2, adresse, emailPersonnel, emailprofessionnel, genre);
                contacts.add(contact);
            }

            Collections.sort(contacts, new Comparator<Contact>() {
                @Override
                public int compare(Contact c1, Contact c2) {
                    return c1.getNom().compareTo(c2.getNom());
                }
            });
            for (Contact contact : contacts) {
                System.out.println("*********************************************************************************");
                System.out.println(ANSI_BLUE+"id: "+ANSI_RESET + contact.getId() + "\n" + ANSI_BLUE+"nom: "+ANSI_RESET + contact.getNom() + "\n" +ANSI_BLUE+ "Prenom: "+ANSI_RESET + contact.getPrenom() + "\n" + ANSI_BLUE+"Telephone1: "+ANSI_RESET + contact.getTel1() +
                        "\n" + ANSI_BLUE+"Telephone2: "+ANSI_RESET + contact.getTel2() + "\n" + ANSI_BLUE+"Adresse:"+ANSI_RESET + contact.getAdresse() + "\n" + ANSI_BLUE+"Email Personnel : "+ANSI_RESET + contact.getEmailPerso()
                        + "\n" + ANSI_BLUE+"Email Professionnelle : "+ANSI_RESET + contact.getEmailPro() + "\n" + ANSI_BLUE+"Genre  : "+ANSI_RESET + contact.getGenre());
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    public void supprimerContact(int id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String sql = "DELETE FROM contact where id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            // Exécuter la requête de suppression du contact de la base de données.
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Le contact a été supprimé avec succès de la base de données.");

                // Supprimer le contact de la liste "contacts".
                for (Contact contact : contacts) {
                    if (contact.getId() == id) {
                        contacts.remove(contact);
                        System.out.println("Le contact a été supprimé avec succès de la liste de contacts.");
                        break;
                    }}
            } else {
                System.out.println("Le contact n'a pas été trouvé dans la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la suppression du contact : " + e.getMessage());
        }
    }

    public void modifierContact(int id) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Veuillez entrer les nouvelles informations pour ce contact :");


            // Retrieve existing contact information from the database
            String query = "SELECT nom,prenom,tel1, tel2, adresse, emailPerso, emailPro, genre FROM contact WHERE id=" + id;
            ResultSet resultSet = stn.executeQuery(query);

            // Retrieve the existing values from the result set
            String nom ="";
            String prenom ="";
            String tel1 = "";
            String tel2 = "";
            String adresse = "";
            String emailPerso = "";
            String emailPro = "";
            String genre = "";

            if (resultSet.next()) {
                nom=resultSet.getString("nom");
                prenom=resultSet.getString("prenom");
                tel1 = resultSet.getString("tel1");
                tel2 = resultSet.getString("tel2");
                adresse = resultSet.getString("adresse");
                emailPerso = resultSet.getString("emailPerso");
                emailPro = resultSet.getString("emailPro");
                genre = resultSet.getString("genre");
            }

            // Prompt the user to choose which attributes to modify
            System.out.println("Quels attributs souhaitez-vous modifier ?");


            System.out.print("Modifier le nom  ? (O / N) : ");
            String modifnom = scanner.next();

            System.out.print("Modifier le prenom  ? (O / N) : ");
            String modifprenom = scanner.next();

            System.out.print("Modifier le téléphone 1 ? (O / N) : ");
            String modifierTel1 = scanner.next();

            System.out.print("Modifier le téléphone 2 ? (O / N) : ");
            String modifierTel2 = scanner.next();

            System.out.print("Modifier l'adresse ? (O / N) : ");
            String modifierAdresse = scanner.next();

            System.out.print("Modifier l'email personnel ? (O / N) : ");
            String modifierEmailPerso = scanner.next();

            System.out.print("Modifier l'email professionnel ? (O / N) : ");
            String modifierEmailPro = scanner.next();

            System.out.print("Modifier le genre ? (O / N) : ");
            String modifierGenre = scanner.next();

            // Update the selected attributes with new values

            if (modifnom.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouveau nom  : "+ANSI_RESET);
                nom=scanner.next();
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier le nom.");
            }

            if (modifprenom.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouveau prenom  : "+ANSI_RESET);
                prenom=scanner.next();
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier le prenom.");
            }

            if (modifierTel1.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouveau téléphone 1 : "+ANSI_RESET);
                String input = scanner.next();
                if (input.matches("^\\+212[67]\\d{8}$")) {
                    tel1 = input;
                } else {
                    System.out.println("Entrée invalide pour le téléphone personnel. Le téléphone sera conservé tel qu'il est.");
                }
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier le telephone 1.");
            }


            if (modifierTel2.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouveau téléphone 2 : "+ANSI_RESET);
                String input = scanner.next();
                if (input.matches("^\\+212[67]\\d{8}$")) {
                    tel2 = input;
                } else {
                    System.out.println("Entrée invalide pour le téléphone professionel. Le téléphone sera conservé tel qu'il est.");
                }
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier le telephone 2.");
            }


            if (modifierAdresse.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouvelle adresse : "+ANSI_RESET);
                String input = scanner.next();
                if (input.matches("^[a-zA-Z0-9._%+-]+$")) {
                    adresse = input;
                } else {
                    System.out.println("Entrée invalide pour l'adresse. L'adresse sera conservée telle qu'elle est.");
                    adresse = scanner.next();  // Mettre à jour la valeur de adresse
                }
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier l' adresse'.");
            }



            if (modifierEmailPerso.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouvel email personnel : "+ANSI_RESET);
                String input = scanner.next();
                if (input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    emailPerso = input;
                } else {
                    System.out.println("Entrée invalide pour l'email personnel'. L'aimail' sera conservé tel qu'il est.");
                }
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier l'email personnel.");
            }

            if (modifierEmailPro.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouvel email professionnel : "+ANSI_RESET);
                String input = scanner.next();
                if (input.matches("^[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9]+.+[a-zA-Z0-9]$")) {
                    emailPro = input;
                } else {
                    System.out.println("Entrée invalide pour l'email professionel'. L'aimail  sera conservé tel qu'il est.");
                }
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier l' email proffesionel'.");
            }

            if (modifierGenre.equalsIgnoreCase("o")) {
                System.out.print(ANSI_BLUE+"Nouveau genre (M/F) : "+ANSI_RESET);
                String input = scanner.next();
                if (input.matches("^[MFmf]$")) {
                    genre = input;
                } else {
                    System.out.println("Entrée invalide pour le genre'. Le genre  sera conservé tel qu'il est.");
                }
            }else {
                System.out.println("Veuillez choisir entre O ou o pour modifier le genre.");
            }

            // Update the contact with the modified values
            query = "UPDATE contact SET nom = '" + nom + "', prenom = '" + prenom + "', tel1 = '" + tel1 + "', tel2= '" + tel2 + "', adresse = '" + adresse + "', emailPerso = '" + emailPerso + "', emailPro = '" + emailPro + "', genre = '" + genre + "' WHERE id = " + id;
            stn.executeUpdate(query);
            System.out.println("Le contact a été modifié avec succès ds bd");
        } catch (Exception ex) {
            System.out.println("Erreur lors de la modification du contact : " + ex);
        }


    }


    public Contact rechercherContactParNom(String nom) {
        try {
            System.out.println("Voici les contacts trouvé avec ce nom : ");
            // Préparer la requête SQL en utilisant SOUNDEX
            String sql = "SELECT * FROM contact WHERE SOUNDEX(nom) = SOUNDEX(?)";
            // Préparer l'instruction SQL avec l'ID du contact
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nom);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                do {
                    int id = rs.getInt("id");
                    String nom1 = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String telephone1 = rs.getString("tel1");
                    String telephone2 = rs.getString("tel2");
                    String adresse = rs.getString("adresse");
                    String emailPersonnel = rs.getString("emailPerso");
                    String emailprofessionnel = rs.getString("emailpro");
                    String genre = rs.getString("genre");
                    Contact contact = new Contact(id, nom1, prenom, telephone1, telephone2, adresse, emailPersonnel, emailprofessionnel, genre);
                    System.out.println("*******************************************************************************");
                    System.out.println(ANSI_BLUE+"id: "+ANSI_RESET + contact.getId() + "\n" + ANSI_BLUE+"nom: "+ANSI_RESET + contact.getNom() + "\n" +ANSI_BLUE+ "Prenom: "+ANSI_RESET + contact.getPrenom() + "\n" + ANSI_BLUE+"Telephone1: "+ANSI_RESET + contact.getTel1() +
                            "\n" + ANSI_BLUE+"Telephone2: "+ANSI_RESET + contact.getTel2() + "\n" + ANSI_BLUE+"Adresse:"+ANSI_RESET + contact.getAdresse() + "\n" + ANSI_BLUE+"Email Personnel : "+ANSI_RESET + contact.getEmailPerso()
                            + "\n" + ANSI_BLUE+"Email Professionnelle : "+ANSI_RESET + contact.getEmailPro() + "\n" + ANSI_BLUE+"Genre  : "+ANSI_RESET + contact.getGenre());


                } while (rs.next());
            } else {
                System.out.println("Désolée , Aucun contact trouvé avec ce nom !!!!!");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la recherche du contact : " + e.getMessage());
        }
        return null;
    }

    public Contact rechercherContactParNumperso(String tel1){
        try{
            System.out.println("Voici les contacts trouvé avec ce numero : "+tel1);
            String sql = "SELECT * FROM contact WHERE tel1=?";
            // Préparer l'instruction SQL avec l'ID du contact.
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, tel1);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                do {
                    int id = rs.getInt("id");
                    String nom1 = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String telephone1 = rs.getString("tel1");
                    String telephone2 = rs.getString("tel2");
                    String adresse = rs.getString("adresse");
                    String emailPersonnel = rs.getString("emailPerso");
                    String emailprofessionnel = rs.getString("emailpro");
                    String genre = rs.getString("genre");
                    Contact contact = new Contact(id, nom1, prenom, telephone1, telephone2, adresse, emailPersonnel, emailprofessionnel, genre);
                    System.out.println("*******************************************************************************");
                    System.out.println(ANSI_BLUE+"id: "+ANSI_RESET + contact.getId() + "\n" + ANSI_BLUE+"nom: "+ANSI_RESET + contact.getNom() + "\n" +ANSI_BLUE+ "Prenom: "+ANSI_RESET + contact.getPrenom() + "\n" + ANSI_BLUE+"Telephone1: "+ANSI_RESET + contact.getTel1() +
                            "\n" + ANSI_BLUE+"Telephone2: "+ANSI_RESET + contact.getTel2() + "\n" + ANSI_BLUE+"Adresse:"+ANSI_RESET + contact.getAdresse() + "\n" + ANSI_BLUE+"Email Personnel : "+ANSI_RESET + contact.getEmailPerso()
                            + "\n" + ANSI_BLUE+"Email Professionnelle : "+ANSI_RESET + contact.getEmailPro() + "\n" + ANSI_BLUE+"Genre  : "+ANSI_RESET + contact.getGenre());
                } while (rs.next());
            } else {
                System.out.println("Désolée , Aucun contact trouvé avec ce numero personnel !!!");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la recherche du contact : " + e.getMessage());
        }
        return null;
    }


    public Contact rechercherContactParNumpro(String tel2){
        try{
            System.out.println("Voici les contacts trouvé avec ce numero : "+tel2);
            String sql = "SELECT * FROM contact WHERE tel2=?";
            // Préparer l'instruction SQL avec l'ID du contact.
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, tel2);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                do {
                    int id = rs.getInt("id");
                    String nom1 = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String telephone1 = rs.getString("tel1");
                    String telephone2 = rs.getString("tel2");
                    String adresse = rs.getString("adresse");
                    String emailPersonnel = rs.getString("emailPerso");
                    String emailprofessionnel = rs.getString("emailpro");
                    String genre = rs.getString("genre");
                    Contact contact = new Contact(id, nom1, prenom, telephone1, telephone2, adresse, emailPersonnel, emailprofessionnel, genre);
                    System.out.println("*******************************************************************************");
                    System.out.println("id: " + contact.getId() + "\n" + "nom: " + contact.getNom() + "\n" + "Prenom: " + contact.getPrenom() + "\n" + "Telephone1: " + contact.getTel1() +
                            "\n" + "Telephone2: " + contact.getTel2() + "\n" + "Adresse:" + contact.getAdresse() + "\n" + "Email Personnel : " + contact.getEmailPerso()
                            + "\n" + "Email Professionnelle : " + contact.getEmailPro() + "\n" + "Genre  : " + contact.getGenre());
                } while (rs.next());
            } else {
                System.out.println("Désoléé, Aucun contact trouvé avec ce numero professionelle .");
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la recherche du contact : " + e.getMessage());
        }
        return null;
    }

    public Contact trouvercontactparid(int id) {
        List<Contact> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM contact WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(resultToContact(rs));
            }
            for (Contact it : list) {
                System.out.println("id: " + it.getId() + "\n" + "Nom: " + it.getNom() + "\n" + "Prenom: " + it.getPrenom() + "\n" + "Télephone perso: " + it.getTel1() + "\n"
                        + "Téléphone professionel: " + it.getTel2() + "\n" + "Adresse: " + it.getAdresse() + "Email Professionnel: " + it.getEmailPro() + "\n" +
                        "Email Personnel: " + it.getEmailPerso() + "\n" + "Genre: " + it.getGenre() + "\n");
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'exécution de la requête : " + ex.getMessage());
            ex.printStackTrace();
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    private Contact resultToContact(ResultSet rs) throws SQLException {
        return new Contact(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),
                rs.getString("tel1"),rs.getString("tel2"),rs.getString("adresse"),rs.getString("emailperso"), rs.getString("emailpro"),rs.getString("genre"));
}
    }


