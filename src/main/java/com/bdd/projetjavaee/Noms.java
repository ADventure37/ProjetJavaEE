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

public class Noms {
    private Connection connexion;

    public List<Eleve> recupererEleves() {
        List<Eleve> Eleves = new ArrayList<Eleve>();
        Statement statement = null;
        ResultSet resultat = null;

        loadDatabase();

        try {
            statement = connexion.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery("SELECT nom, prenom, genre, sitePrecedent, formationPrecedente FROM eleve;");

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
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO eleve(nom, prenom, genre, sitePrecedent, formationPrecedente) VALUES(?, ?, ?, ?, ?);");
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
}