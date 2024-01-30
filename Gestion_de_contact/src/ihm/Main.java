package ihm;

import bo.Contact;
import bo.Groupe;
import data.ContactDAO;
import data.GroupeDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static bll.GestionContacts.ajouterContact;
import static bll.GestionContacts.supprimerContact;


public class Main {
    public static final String ANSI_BLUE = "\u001B[96m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static ContactDAO modif=new ContactDAO();
    private static GroupeDAO modifgrp=new GroupeDAO();


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root", "");
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        ContactDAO contactDAO = new ContactDAO();

        // Boucle principale
        while (continuer) {
            System.out.println("");
            System.out.println("\033[38;5;208m******************Que voulez-vous faire ?******************\033[0m");
            System.out.println(" ");
            System.out.println("——————————————————————————————————————————————————————————");
            System.out.print("|");System.out.print("\033[38;5;34m1. Ajouter un contact\033[0m");System.out.println("                                   |");
            System.out.print("|");System.out.print("\033[38;5;34m2. Afficher la liste des contacts par ordre alphabétique\033[0m");System.out.println("|");
            System.out.print("|");System.out.print("\033[38;5;34m3. Supprimer un contact\033[0m");System.out.println("                                 |");
            System.out.print("|");System.out.print("\033[38;5;34m4. Modifier un contact\033[0m");System.out.println("                                  |");
            System.out.print("|");System.out.print("\033[38;5;34m5. Rechercher un contact par nom\033[0m");System.out.println("                        |");
            System.out.print("|");System.out.print("\033[38;5;34m6. Rechercher un contact par numéro\033[0m");System.out.println("                     |");
            System.out.print("|");System.out.print("\033[38;5;34m7. Créer un groupe\033[0m");System.out.println("                                      |");
            System.out.print("|");System.out.print("\033[38;5;34m8. Ajouter des contacts à un groupe existant\033[0m");System.out.println("            |");
            System.out.print("|");System.out.print("\033[38;5;34m9. Supprimer d'un groupe\033[0m");System.out.println("                                |");
            System.out.print("|");System.out.print("\033[38;5;34m10. Rechercher un groupe par Nom\033[0m");System.out.println("                        |");
            System.out.print("|");System.out.print("\033[38;5;34m11.afficher les contacts dans un grp\033[0m");System.out.println("                    |");
            System.out.print("|");System.out.print("\033[38;5;34m12. Quitter\033[0m");System.out.println("                                             |");
            System.out.println("——————————————————————————————————————————————————————————");


            // Lecture de l'entrée utilisateur
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    ajouterContact();
                    break;
                case 2:
                    contactDAO.afficherContacts();
                    break;
                case 3:
                    System.out.println("Veuillez entrer l'ID du contact à supprimer :");
                    int id = scanner.nextInt();
                    supprimerContact(id);
                    break;
                case 4:
                    System.out.println("Veuillez entrer l'ID du contact à modifier :");
                    int idModif = scanner.nextInt();
                    modif.modifierContact(idModif);
                    break;
                case 5:
                    System.out.println("Veuillez entrer le nom du contact à rechercher :");
                    String nomRecherche = scanner.next();
                    modif.rechercherContactParNom(nomRecherche);
                    break;
                case 6:
                    while (true) {
                        System.out.println("Quel numero voullez vous le rechercher :\n" +
                                "1. Num personnel \n" +
                                "2. Num  proffessionel :\n" +
                                "3.fin de recherche"
                        );
                        int choix1 = scanner.nextInt();
                        switch (choix1) {
                            case 1:
                                System.out.println("Veuillez entrer le num perso du contact à rechercher :");
                                String num1Recherche = scanner.next();
                                modif.rechercherContactParNumperso(num1Recherche);

                                break;
                            case 2:
                                System.out.println("Veuillez entrer le num pro du contact à rechercher :");
                                String num2Recherche = scanner.next();
                                modif.rechercherContactParNumpro(num2Recherche);
                                break;
                            case 3:
                                System.out.println("Fin de la recherche par Numero !");
                                break;
                            default:
                            System.out.println("Choix invalide !");
                            }break;}break;

                case 7:
                    System.out.println("Veuillez entrer le nom du groupe :");
                    String nomGroupe = scanner.next();
                    modifgrp.creerGroupe(nomGroupe);
                    break;

                case 8:
                    GroupeDAO groupeDAO = new GroupeDAO();
                    groupeDAO.ajouterContactAuGroupe(connection);

                    break;
                case 9:
                    modifgrp.supprimerGroupe(connection);
                    break;

                case 10:
                    modifgrp.rechercherGroupeParNom(connection);
                    break;
                case 11:
                    modifgrp.rechercherContactDansGroupe(connection);
                    break;

                case 12:
                    System.out.println(ANSI_BLUE+"Au revoir chaymae !"+ANSI_RESET);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide !");
            }
        }

    }}



