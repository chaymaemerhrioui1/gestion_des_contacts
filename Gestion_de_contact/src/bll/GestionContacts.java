package bll;

import bo.Contact;
import bo.Groupe;
import data.ContactDAO;
import data.GroupeDAO;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.sql.*;


public class GestionContacts {

    private static List<Contact> contacts = new ArrayList<>();
    private static int currentId = 1;
    private static ContactDAO contactController=new ContactDAO();


        public static void ajouterContact() {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Prénom : * ");
            String prenom = scanner.nextLine();


            //expression reguliere de tele1
            System.out.print("Téléphone 1 : * ");
            String tel1 = "";
            do {
                System.out.printf("saisie votre numperso : exemple: +212 6XX XXX XXX  ");
                tel1 = scanner.nextLine();
            } while (!tel1.isEmpty() &&!tel1.matches("^\\+212[67]\\d{8}$"));


            //expression reguliere de tele2
            System.out.print("Téléphone 2 : ");
            String tel2 = "";
            do {
                System.out.printf("saisie votre numpro : exemple: +212 6XX XXX XXX  ");
                tel2 = scanner.nextLine();
            } while (!tel2.isEmpty() &&!tel2.matches("^\\+212[67]\\d{8}$"));

            System.out.print("Adresse : ");
            String adresse = scanner.nextLine();
            //expression reguliere de email personnel

            System.out.print("Email personnel : * ");
            String emailPerso;
            do {
                System.out.print("Saisissez l'email  : exemple:xx@xx.com");
                emailPerso = scanner.nextLine();
            } while (!emailPerso.isEmpty() && !emailPerso.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));



            //expression reguliere de email pro
            System.out.print("Email proffesionel : ");
            String emailPro = "";
            do {
                System.out.printf("saisie lemail : exemple:xx@xx.xx  ");
                emailPro = scanner.nextLine();
            } while (!emailPro.isEmpty() &&!emailPro.matches("^[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9]+.+[a-zA-Z0-9]$"));


            //expression reguliere de genre
            System.out.print("Genre (M/F) : ");
            String genre = "";
            do {
                System.out.printf("saisie le genre , f pour femme, m pour homme  ");
                genre = scanner.nextLine();
            } while (!genre.isEmpty() &&!genre.matches("^[MFmf]$"));
            //System.out.print("Genre (M/F) :");
            if (prenom.isEmpty()||tel1.isEmpty()||emailPerso.isEmpty()){
                System.out.println("Veillez remplir tous les champs obligatoires !! Saisir à nouveaux vos informations .  ");
                return;
            }



            Contact contact = new Contact(currentId++, nom, prenom, tel1, tel2, adresse, emailPerso, emailPro, genre);
            contacts.add(contact);
            System.out.println("Le contact a été ajouté avec succès dans la liste  !");

            //appel database
            contactController.ajouterContact(contact);}

        public static void supprimerContact(int id) {
            contactController.supprimerContact(id);
            for (Contact contact : contacts) {
                if (contact.getId() == id) {
                    contacts.remove(contact);
                    System.out.println("Le contact a été supprimé avec succès.");
                    return;
                }}}


        private static void rechercherContactParNumperso(String tel1) {

            boolean contactTrouve = false;
            for (Contact contact : contacts) {
                if (contact.getTel1().equals(tel1)) {
                    System.out.println(contact);
                    contactTrouve = true;
                }}
            if (!contactTrouve) {
                System.out.println("Aucun contact trouvé avec ce numero.");
            }}

        private static void rechercherContactParNumpro(String tel2) {

            boolean contactTrouve = false;
            for (Contact contact : contacts) {
                if (contact.getTel1().equals(tel2)) {
                    System.out.println(contact);
                    contactTrouve = true;
                }}
            if (!contactTrouve) {
                System.out.println("Aucun contact trouvé avec ce numero.");
            }}

        private static Contact trouverContactParId(int id) {
            for (Contact contact : contacts) {
                if (contact.getId() == id) {
                    return contact;
                }
            }
            System.out.println("Aucun contact n'a été trouvé avec l'ID " + id);
            return null;
        }


        private static void creerGroupe(String nomGroupe) {
            List<String> groupes = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            // Création du groupe
            List<Contact> groupe = new ArrayList<>();

            // Ajout des contacts existants dans le groupe
            System.out.println("Voulez-vous ajouter des contacts existants au groupe ? (O/N)");
            String choix = scanner.next();
            if (choix.equalsIgnoreCase("O")) {
                System.out.println("Veuillez entrer les nom des contacts à ajouter au groupe, séparés par des virgules :");
                String input = scanner.next();
                String[] ids = input.split(",");
                for (String idStr : ids) {
                    int id = Integer.parseInt(idStr.trim());
                    Contact contact = trouverContactParId(id);
                    if (contact != null) {
                        groupe.add(contact);
                        contact.getGroupes().add(nomGroupe);
                    }
                }
            }
            // Ajout du groupe à la liste des groupes
            //boolean groupeExiste = false;
            boolean groupeExiste = false;
            for (String key : groupes) {
                if (key.equalsIgnoreCase(nomGroupe)) {
                    groupeExiste = true;
                    break;
                }
            }
            if (groupeExiste) {
                System.out.println("Le groupe " + nomGroupe + " existe déjà.");
            } else {
                Groupe groupe1 = new Groupe(nomGroupe);
                groupes.add(String.valueOf(groupe1));
                System.out.println("Le groupe " + nomGroupe + " a été créé avec succès.");
            }

        }

        private static void supprimerContactDuGroupe(String nomGroupe) {
            List<String> groupes = new ArrayList<>();
            String groupe = groupes.get(Integer.parseInt(nomGroupe));
            if (groupe != null) {
                groupes.remove(nomGroupe);

                System.out.println("Le groupe " + nomGroupe + " a été supprimé avec succès.");
            } else {
                System.out.println("Le groupe " + nomGroupe + " n'existe pas.");
            }
        }
    }
















