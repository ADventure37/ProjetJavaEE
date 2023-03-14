package com.servlet.projetjavaee;

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
import java.util.StringTokenizer;



@WebServlet(name = "Page1", value = "/Page1")
public class Page1 extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private List<Eleve> eleves = new ArrayList<Eleve>();

    public static final int TAILLE_TAMPON = 10240;
    public static final String CHEMIN_FICHIERS = "C:\\Users\\adrie\\Downloads\\ProjetJavaEE\\CSV\\"; // A changer


    public Page1() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String action = request.getParameter("bouton");
        if("Valider".equals(action)){
            String nom = request.getParameter("Nom");
            String prenom = request.getParameter("Prenom");
            String genre = request.getParameter("genre");
            String site = request.getParameter("sitePrecedent");
            String formation = request.getParameter("formationPrecedente");
            eleves.add(new Eleve(nom, prenom, genre, site, formation));


        } else if ("Cliquez".equals(action)) {
        } else if ("Valider le fichier".equals(action)) {
            // On récupère le champ description comme d'habitude
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
        request.getSession().setAttribute("eleves", eleves);
        System.out.println(eleves);
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    private void ecrireFichier( Part part, String nomFichier) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(CHEMIN_FICHIERS + nomFichier)), TAILLE_TAMPON);

            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
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

    private static String getNomFichier( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }

    private void csvToEleve(Part part){
        String nomFichier = getNomFichier(part);
        String adresse = CHEMIN_FICHIERS + nomFichier;
        try {
            CSVReader reader = new CSVReader(new FileReader(adresse));

            // Lecture des lignes suivantes contenant les données
            String[] ligne;
            reader.readNext();
            while ((ligne = reader.readNext()) != null) {
                String nom = ligne[0];
                String prenom = ligne[1];
                String genre = ligne[2];
                String site = ligne[3];
                String formation = ligne[4];
                eleves.add(new Eleve(nom, prenom, genre, site, formation));

                // Affichage des informations extraites
//                System.out.println("Nom : " + nom);
//                System.out.println("Prenom : " + prenom);
//                System.out.println("genre : " + genre);
//                System.out.println("site : " + site);
//                System.out.println("formation : " + formation);
//                System.out.println();
            }

            // Fermeture du lecteur de fichier
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        suppFile(adresse);
    }

    private void suppFile(String adresse){
        File file = new File(adresse);
        file.delete();
    }
    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }
}
