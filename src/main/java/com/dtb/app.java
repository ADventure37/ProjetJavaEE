package com.dtb;
import java.sql.*;

public class app {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Projet_JEE";
        String user = "root";
        String password = "root";
        Connection conn = null;

        try {
            // Établir une connexion à la base de données
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion établie avec la base de données MySQL !");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM projet_jee.eleve");
            while (rs.next()){
                String nom = rs.getString("nom");
                System.out.println(nom);
            }


            // Utiliser la connexion pour effectuer des opérations sur la base de données

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        } finally {
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
    }
}
