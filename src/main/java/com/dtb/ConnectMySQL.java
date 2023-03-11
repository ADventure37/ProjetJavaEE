package com.dtb;
import com.beans.projetjavaee.Eleve;
import com.beans.projetjavaee.Equipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectMySQL {
    private String url = "jdbc:mysql://localhost:3306/Projet_JEE";
    private String user = "root";
    private String psw = "root";
    private Connection conn = null;
    private List<Eleve> eleves = new ArrayList<>();
    private List<Equipe> equipes = new ArrayList<>();


    public void ouvreConn() {
        try {
            // Établir une connexion à la base de données
            this.setConn(DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPsw()));
            System.out.println("Connexion établie avec la base de données MySQL !");
            // Utiliser la connexion pour effectuer des opérations sur la base de données
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public void fermeConn(){
        // Fermer la connexion à la base de données
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connexion à la base de données fermée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion à la base de données : " + e.getMessage());
        }
    }

    public List<Eleve> listeEleve() throws SQLException {
        Statement stmt = this.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM eleve");
        while (rs.next()){
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String genre = rs.getString("genre");
            String siteP = rs.getString("sitePrecedent");
            String formP = rs.getString("formationPrecedente");
            String equipe = rs.getString("equipe");
            System.out.println(nom);
            Eleve e = new Eleve(nom, prenom, genre, siteP, formP);
            e.setEquipe(equipe);
        }
        return this.getEleves();
    }
    public List<Equipe> listeEquipe() throws SQLException {
        Statement stmt = this.getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM eleve");
        while (rs.next()){
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String genre = rs.getString("genre");
            String siteP = rs.getString("sitePrecedent");
            String formP = rs.getString("formationPrecedente");
            String equipe = rs.getString("equipe");
            System.out.println(nom);
            Eleve e = new Eleve(nom, prenom, genre, siteP, formP);
            e.setEquipe(equipe);
        }
        return this.getEquipes();
    }

    private String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }

    private String getPsw() {
        return psw;
    }

    private void setPsw(String psw) {
        this.psw = psw;
    }

    private Connection getConn() {
        return conn;
    }

    private void setConn(Connection conn) {
        this.conn = conn;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }
}
