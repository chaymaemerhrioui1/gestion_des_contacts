package data;

import bo.Contact;
import bo.Groupe;
import bo.Groupe_contact;

import java.sql.*;
import java.util.*;

import static bo.Contact.*;

public class GroupeDAO {
    private static Connection con;
    private Statement stn;
    private ContactDAO contactDAO=new ContactDAO();
    private List<Contact> contacts=new ArrayList<>();

    public GroupeDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root", "");
            stn = con.createStatement();
            System.out.println("Bienvenue ,votre connection est bien connecte !!");
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }

    public static void creerGroupe(String nomGroupe) {
                            try {
                                // Vérifier si le groupe existe déjà
                                String checkQuery = "SELECT COUNT(*) FROM groupe WHERE nom = ?";
                                PreparedStatement checkStatement = con.prepareStatement(checkQuery);
                                checkStatement.setString(1, nomGroupe);
                                ResultSet resultSet = checkStatement.executeQuery();
                                resultSet.next();
                                int count = resultSet.getInt(1);

                                if (count > 0) {
                                    System.out.println("Le groupe " + nomGroupe + " existe déjà dans la base de données.");
                                    return;
                                }

                                // Créer une instruction SQL pour insérer un nouveau groupe dans la table "groupe".
                                String insertQuery = "INSERT INTO groupe (nom) VALUES (?)";
                                PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                                insertStatement.setString(1, nomGroupe);

                                // Exécuter l'instruction SQL pour insérer le nouveau groupe.
                                int rowsInserted = insertStatement.executeUpdate();
                                if (rowsInserted > 0) {
                                    System.out.println("Le groupe " + nomGroupe + " a été créé avec succès dans la base de données.");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }


    public void ajouterContactAuGroupe(Connection connection) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nom du groupe : ");
        String nomGroupe = scanner.nextLine();

        System.out.print("ID du contact à ajouter : ");
        int idContact = scanner.nextInt();
        scanner.nextLine();

        // Vérifier si le groupe existe
        String selectGroupeSQL = "SELECT id FROM groupe WHERE nom = ?";
        try (PreparedStatement selectGroupeStatement = connection.prepareStatement(selectGroupeSQL)) {
            selectGroupeStatement.setString(1, nomGroupe);
            ResultSet groupeResultSet = selectGroupeStatement.executeQuery();

            if (groupeResultSet.next()) {
                int idGroupe = groupeResultSet.getInt("id");

                // Vérifier si le contact existe
                String selectContactSQL = "SELECT id FROM contact WHERE id = ?";
                try (PreparedStatement selectContactStatement = connection.prepareStatement(selectContactSQL)) {
                    selectContactStatement.setInt(1, idContact);
                    ResultSet contactResultSet = selectContactStatement.executeQuery();

                    if (contactResultSet.next()) {
                        // Ajouter le contact au groupe
                        String insertGroupeContactSQL = "INSERT INTO groupe_contact (id_groupe, id_contact) VALUES (?, ?)";
                        try (PreparedStatement insertGroupeContactStatement = connection.prepareStatement(insertGroupeContactSQL)) {
                            insertGroupeContactStatement.setInt(1, idGroupe);
                            insertGroupeContactStatement.setInt(2, idContact);
                            insertGroupeContactStatement.executeUpdate();

                            System.out.println("Le contact a été ajouté au groupe.");
                        } catch (SQLException e) {
                            System.out.println("Erreur lors de l'ajout du contact au groupe : " + e.getMessage());
                        }
                    } else {
                        System.out.println("Le contact avec l'ID " + idContact + " n'existe pas.");
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la recherche du contact : " + e.getMessage());
                }
            } else {
                System.out.println("Le groupe avec le nom '" + nomGroupe + "' n'existe pas.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du groupe : " + e.getMessage());
}
}

    public void supprimerGroupe(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nom du groupe à supprimer : ");
        String nomGroupe = scanner.nextLine();

        // Vérifier si le groupe existe
        String selectGroupeSQL = "SELECT id FROM groupe WHERE nom = ?";
        try (PreparedStatement selectGroupeStatement = connection.prepareStatement(selectGroupeSQL)) {
            selectGroupeStatement.setString(1, nomGroupe);
            ResultSet groupeResultSet = selectGroupeStatement.executeQuery();

            if (groupeResultSet.next()) {
                int idGroupe = groupeResultSet.getInt("id");

                // Supprimer les références dans la table contact_group
                String deleteContactGroupSQL = "DELETE FROM groupe_contact WHERE id_groupe = ?";
                try (PreparedStatement deleteContactGroupStatement = connection.prepareStatement(deleteContactGroupSQL)) {
                    deleteContactGroupStatement.setInt(1, idGroupe);
                    deleteContactGroupStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression des références dans la table contact_group : " + e.getMessage());
                    return; // Arrêter le processus de suppression du groupe
                }

                // Supprimer le groupe de la table groupe
                String deleteGroupeSQL = "DELETE FROM groupe WHERE id = ?";
                try (PreparedStatement deleteGroupeStatement = connection.prepareStatement(deleteGroupeSQL)) {
                    deleteGroupeStatement.setInt(1, idGroupe);
                    deleteGroupeStatement.executeUpdate();

                    System.out.println("Le groupe a été supprimé.");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression du groupe : " + e.getMessage());
                }
            } else {
                System.out.println("Le groupe avec le nom '" + nomGroupe + "' n'existe pas.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du groupe : " + e.getMessage());
        }
    }



    public void rechercherGroupeParNom(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nom du groupe à rechercher : ");
        String nomGroupe = scanner.nextLine();
        // Rechercher le groupe par son nom
        String selectGroupeSQL = "SELECT * FROM groupe WHERE nom = ?";
        try (PreparedStatement selectGroupeStatement = connection.prepareStatement(selectGroupeSQL)) {
            selectGroupeStatement.setString(1, nomGroupe);
            ResultSet groupeResultSet = selectGroupeStatement.executeQuery();
            if (groupeResultSet.next()) {
                int idGroupe = groupeResultSet.getInt("id");
                String nom = groupeResultSet.getString("nom");
                System.out.println("******************** Groupe trouvé : ********************");
                System.out.println("ID : " + idGroupe);
                System.out.println("Nom : " + nom);
            } else {
                System.out.println("Le groupe avec le nom '" + nomGroupe + "' n'existe pas.");}
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du groupe : " + e.getMessage());}}

public void rechercherContactDansGroupe(Connection connection) {
    Scanner scanner = new Scanner(System.in);
    List<Groupe_contact> list = new ArrayList<>();
    ContactDAO contactDAO=new ContactDAO();
    System.out.print("Nom du groupe à rechercher : ");
    String nomGroupe = scanner.nextLine();
    // Rechercher le groupe par son nom
    String selectGroupeSQL = "SELECT * FROM groupe WHERE nom = ?";
    try (PreparedStatement selectGroupeStatement = connection.prepareStatement(selectGroupeSQL)) {
        selectGroupeStatement.setString(1, nomGroupe);
        ResultSet groupeResultSet = selectGroupeStatement.executeQuery();
        if (groupeResultSet.next()) {
            int idGroupe = groupeResultSet.getInt("id");
            String nom = groupeResultSet.getString("nom");
            System.out.println("Groupe trouvé :");
            System.out.println("ID : " + idGroupe);
            System.out.println("Nom : " + nom);

            PreparedStatement stm = connection.prepareStatement("SELECT * from groupe_contact where id_groupe= ?");
            stm.setInt(1,idGroupe);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(resultToContact(rs));

            } for (Groupe_contact it:list) {
                System.out.println("-----------------------------------------------------");
                System.out.println("les informations concernants les contacts qui appartiennent au (Groupe: "+nomGroupe+")");
                int idcontact=it.getId_contact();
                contactDAO.trouvercontactparid(idcontact);
            }
            rs.close();
        } else {
            System.out.println("Le groupe avec le nom '" + nomGroupe + "' n'existe pas.");
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la recherche du groupe : " + e.getMessage());
}}



private Groupe_contact resultToContact(ResultSet rs) throws SQLException {
    return new Groupe_contact(rs.getInt("id"),rs.getInt("id_groupe"),rs.getInt("id_contact"));
}

    public void creerGroupesAutomatiques() {
        Map<String, List<Contact>> groupes = new HashMap<>();

        for (Contact contact : contacts) {
            String nom = contact.getNom();
            if (groupes.containsKey(nom)) {
                groupes.get(nom).add(contact);
            } else {
                List<Contact> groupe = new ArrayList<>();
                groupe.add(contact);
                groupes.put(nom, groupe);
            }
        }

        for (Map.Entry<String, List<Contact>> entry : groupes.entrySet()) {
            String nomGroupe = entry.getKey();
            List<Contact> membres = entry.getValue();

            System.out.println("Groupe : " + nomGroupe);
            for (Contact membre : membres) {
                System.out.println(membre.getNom() + " " + membre.getPrenom());
            }
            System.out.println("-------------------------------------------------");
        }
    }

//optionnel
/*public void creerGroupesAut(Connection connection) {
    try {
        // Récupérer tous les noms distincts des contacts
        String selectNomsSQL = "SELECT DISTINCT nom FROM contact";
        PreparedStatement selectNomsStatement = connection.prepareStatement(selectNomsSQL);
        ResultSet nomsResultSet = selectNomsStatement.executeQuery();

        while (nomsResultSet.next()) {
            String nomContact = nomsResultSet.getString("nom");

            // Récupérer tous les contacts ayant le même nom
            String selectContactsSQL = "SELECT * FROM contact WHERE nom = ?";
            PreparedStatement selectContactsStatement = connection.prepareStatement(selectContactsSQL);
            selectContactsStatement.setString(1, nomContact);
            ResultSet contactsResultSet = selectContactsStatement.executeQuery();

            // Créer un groupe avec le nom du contact
            creerGroupe(nomContact);

            // Ajouter tous les contacts ayant le même nom au groupe
            while (contactsResultSet.next()) {
                int idContact = contactsResultSet.getInt("id");
                ajouterContactAuGroupe(connection);
            }
        }

        System.out.println("Groupes créés avec succès !");
    } catch (SQLException e) {
        System.out.println("Une erreur s'est produite lors de la création des groupes : " + e.getMessage());
    }
}*/

}
