package com.servlet.projetjavaee;

import com.beans.projetjavaee.Eleve;
import com.dtb.ConnectMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "Page1", value = "/Page1")
public class Page1 extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private List<Eleve> eleves = new ArrayList<Eleve>();

    public static final int TAILLE_TAMPON = 10240;
    public static final String CHEMIN_FICHIERS = "C:\\Users\\User\\Desktop\\Intellij\\ProjetJavaEE\\CSV\\"; // A changer


    public Page1() {
        super();
        eleves.add(new Eleve("Deloy", "Adrien", "Homme", "Angers", "MPSI"));

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
        System.out.println(action);
        if("Valider".equals(action)){
            String nom = request.getParameter("Nom");
            String prenom = request.getParameter("Prenom");
            String genre = request.getParameter("genre");
            String site = request.getParameter("sitePrecedent");
            String formation = request.getParameter("formationPrecedente");
            eleves.add(new Eleve(nom, prenom, genre, site, formation));
            request.getSession().setAttribute("eleves", eleves);

        } else if ("Cliquez".equals(action)) {
            request.getSession().setAttribute("eleves", eleves);
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
                ecrireFichier(part, nomFichier, CHEMIN_FICHIERS);

                request.setAttribute(nomChamp, nomFichier);
            }
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

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

    private void suppFile(Part part){
        String nom = getNomFichier(part);
        String adresse = CHEMIN_FICHIERS + nom;
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
