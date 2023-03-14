package com.bdd.projetjavaee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.beans.projetjavaee.Eleve;
import com.beans.projetjavaee.Equipe;

public class Noms {
    private Connection connexion;

    public List<Eleve> recupererElevesS() {
        List<Eleve> Eleves = new ArrayList<Eleve>();
        Statement statement = null;
        ResultSet resultat = null;

        loadDatabase();

        try {
            statement = connexion.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery(
                    "SELECT nom, prenom, genre, sitePrecedent, formationPrecedente FROM eleve where idEquipe is NULL;");

            // Récupération des données
            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String genre = resultat.getString("genre");
                String sitePrecedent = resultat.getString("sitePrecedent");
                String formationPrecedente = resultat.getString("formationPrecedente");

                Eleve Eleve = new Eleve();
                Eleve.setNom(nom);
                Eleve.setPrenom(prenom);
                Eleve.setGenre(genre);
                Eleve.setSitePrecedent(sitePrecedent);
                Eleve.setFormationPrecedente(formationPrecedente);

                Eleves.add(Eleve);
            }
        } catch (SQLException e) {
        } finally {
            // Fermeture de la connexion
            try {
                if (resultat != null)
                    resultat.close();
                if (statement != null)
                    statement.close();
                if (connexion != null)
                    connexion.close();
            } catch (SQLException ignore) {
            }
        }

        return Eleves;
    }

    public List<Eleve> recupererElevesEq(int idEq) {
        List<Eleve> Eleves = new ArrayList<Eleve>();
        Statement statement = null;
        ResultSet resultat = null;

        loadDatabase();

        try {
            statement = connexion.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery(
                    "SELECT nom, prenom, genre, sitePrecedent, formationPrecedente, idEquipe FROM eleve where idEquipe is NOT NULL;");

            // Récupération des données
            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String genre = resultat.getString("genre");
                String sitePrecedent = resultat.getString("sitePrecedent");
                String formationPrecedente = resultat.getString("formationPrecedente");
                int idEquipe = resultat.getInt("idEquipe");
                if(idEquipe == idEq) {
                    Eleve Eleve = new Eleve();
                    Eleve.setNom(nom);
                    Eleve.setPrenom(prenom);
                    Eleve.setGenre(genre);
                    Eleve.setSitePrecedent(sitePrecedent);
                    Eleve.setFormationPrecedente(formationPrecedente);
                    Eleve.setIdEquipe(idEquipe);
                    Eleves.add(Eleve);
                }
            }
            //System.out.println(Eleves);
        } catch (SQLException e) {
        } finally {
            // Fermeture de la connexion
            try {
                if (resultat != null)
                    resultat.close();
                if (statement != null)
                    statement.close();
                if (connexion != null)
                    connexion.close();
            } catch (SQLException ignore) {
            }
        }

        return Eleves;
    }

    public List<Equipe> recupererEquipes() {
        List<Equipe> equipes = new ArrayList<Equipe>();
        Statement statement = null;
        ResultSet resultat = null;

        loadDatabase();

        try {
            statement = connexion.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery("SELECT * FROM equipe;");

            // Récupération des données
            while (resultat.next()) {
                int id = resultat.getInt("id");
                String nom = resultat.getString("nom");

                Equipe e = new Equipe(nom);
                e.setId(id);
                //System.out.println(recupererElevesEq(e.getId()));
                e.setEleves(recupererElevesEq(e.getId()));
                equipes.add(e);
            }
        } catch (SQLException e) {
        } finally {
            // Fermeture de la connexion
            try {
                if (resultat != null)
                    resultat.close();
                if (statement != null)
                    statement.close();
                if (connexion != null)
                    connexion.close();
            } catch (SQLException ignore) {
            }
        }

        return equipes;
    }

    private void loadDatabase() {
        // Chargement du driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }

        try {
            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaee", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterEleve(Eleve Eleve) {
        loadDatabase();

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "INSERT INTO eleve(nom, prenom, genre, sitePrecedent, formationPrecedente) VALUES(?, ?, ?, ?, ?);");
            preparedStatement.setString(1, Eleve.getNom());
            preparedStatement.setString(2, Eleve.getPrenom());
            preparedStatement.setString(3, Eleve.getGenre());
            preparedStatement.setString(4, Eleve.getSitePrecedent());
            preparedStatement.setString(5, Eleve.getFormationPrecedente());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterEquipe(Equipe equipe){
        loadDatabase();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("" +
                    "INSERT INTO equipe(nom) VALUES(?);");
            preparedStatement.setString(1, equipe.getNom());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifNomEq(String ancienNom, String nouveauNom){
        loadDatabase();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "UPDATE equipe set nom = (?) where nom = (?);");
            preparedStatement.setString(1, nouveauNom);
            preparedStatement.setString(2, ancienNom);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ajouterAEquipe(int idEq, Eleve eleve){
        loadDatabase();
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "UPDATE eleve set idEquipe = (?) where nom = (?) and prenom = (?);");
            preparedStatement.setInt(1, idEq);
            preparedStatement.setString(2, eleve.getNom());
            preparedStatement.setString(3, eleve.getPrenom());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerEl(Eleve eleve) {
        loadDatabase();
        try {
            System.out.println(eleve.getNom());
            PreparedStatement preparedStatement = connexion.prepareStatement(
                        "UPDATE eleve set idEquipe = null where nom = (?) and prenom = (?);");
                preparedStatement.setString(1, eleve.getNom());
                preparedStatement.setString(2, eleve.getPrenom());
                preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}