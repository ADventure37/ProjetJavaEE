package com.servlet.projetjavaee;

import com.bdd.projetjavaee.Noms;
import com.beans.projetjavaee.Eleve;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.Part;
import java.util.StringTokenizer;

import java.io.*;

@WebServlet(name = "Page1", value = "/Page1")
public class Page1 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final int TAILLE_TAMPON = 10240;

    //Chemin où est enregistrer temporairement le csv
    public static final String CHEMIN_FICHIERS = "C:\\Users\\adrie\\Downloads\\ProjetJavaEE\\CSV\\"; // A changer


    public Page1() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Noms tableNoms = new Noms();
        //Définition de l'attribut de la requête qui correspond à la liste des élèves sans équipe
        request.setAttribute("eleves", tableNoms.recupererElevesS());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String action = request.getParameter("bouton");
        
        if("Valider".equals(action)) {
            Eleve eleve = new Eleve();
            //Récupération des informations du formaulaire
            eleve.setNom(request.getParameter("Nom"));
            eleve.setPrenom(request.getParameter("Prenom"));
            eleve.setGenre(request.getParameter("genre"));
            eleve.setSitePrecedent(request.getParameter("sitePrecedent"));
            eleve.setFormationPrecedente(request.getParameter("formationPrecedente"));

            Noms tableNoms = new Noms();
            //Fonction qui permet d'ajouter un élève dans la base de données avec les informations du formulaire
            tableNoms.ajouterEleve(eleve);

            //Définition de l'attribut de la requête qui correspond à la liste des élèves sans équipe
            request.setAttribute("eleves", tableNoms.recupererElevesS());

        } else if ("Valider le fichier".equals(action)) {
            // On récupère le champ description
            String description = request.getParameter("description");
            request.setAttribute("description", description );

            // On récupère le champ du fichier
            Part part = request.getPart("fichier");

            // On vérifie qu'on a bien reçu un fichier
            String nomFichier = getNomFichier(part);

            // Si on a bien un fichier
            if (nomFichier != null && !nomFichier.isEmpty()) {
                String nomChamp = part.getName();
                // Corrige un bug du fonctionnement d'Internet Explorer
                nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
                        .substring(nomFichier.lastIndexOf('\\') + 1);

                // On écrit définitivement le fichier sur le disque
                ecrireFichier(part, nomFichier);
                csvToEleve(part);
                request.setAttribute(nomChamp, nomFichier);
            }

        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    //ecrireFichier permet d'écrire un fichier à partir d'une 'Part' (partie) d'une reqûete HTTP
    private void ecrireFichier( Part part, String nomFichier) throws IOException {

        //Initialisation des entrées et sorties
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            //On utilise un tampon lors de la lecture de la partie de la reqûete HTTP
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);

            //On écrit le fichier en utilisant le même tampon
            sortie = new BufferedOutputStream(new FileOutputStream(new File(CHEMIN_FICHIERS + nomFichier)), TAILLE_TAMPON);

            //Lecture des données de l'entrée
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;

            //Lecture des données de l'entrée puis écriture dans la sortie à l'aide d'une boucle pour couvrir l'ensemble des données
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
            //Fermeture des flux d'entrée et de sortie
            try {
                sortie.close();
            } catch (IOException ignore) {
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            }
        }
    }
    //Récupère les informations du fichier csv pour les intégrer à la base de données sous la forme d'élève
    private void csvToEleve(Part part){
        String nomFichier = getNomFichier(part);
        String adresse = CHEMIN_FICHIERS + nomFichier;
        try {
            CSVReader reader = new CSVReader(new FileReader(adresse));

            // Lecture des lignes suivantes contenant les données
            String[] ligne;
            reader.readNext();
            while ((ligne = reader.readNext()) != null) {
                //Récupération des données de chaque ligne
                String nom = ligne[0];
                String prenom = ligne[1];
                String genre = ligne[2];
                String site = ligne[3];
                String formation = ligne[4];
                Eleve e  = new Eleve(nom, prenom, genre, site, formation);
                Noms tableNoms = new Noms();
                //Ajout d'un élève dans la base de données par ligne
                tableNoms.ajouterEleve(e);

            }

            // Fermeture du lecteur de fichier
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        //Suppression du fichier csv après utilisation
        suppFile(adresse);
    }

    //Suppression du fichier cvs
    private void suppFile(String adresse){
        File file = new File(adresse);
        file.delete();
    }

    //getNomFichier permet d'extraire le nom d'un fichier à partir d'une requête HTTP
    private static String getNomFichier( Part part ) {
        //Parcour des différentes parties du champ "content-disposition"
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            //On vérifie que la partie commence par "filename"
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                //On extrait le nom en retirant les espaces et guillemets
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }
}
