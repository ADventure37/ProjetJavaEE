package com.servlet.projetjavaee;

import com.bdd.projetjavaee.Noms;
import com.beans.projetjavaee.Eleve;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.Part;

import java.io.*;

@WebServlet(name = "Page1", value = "/Page1")
public class Page1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final int TAILLE_TAMPON = 10240;
    public static final String CHEMIN_FICHIERS = "D:/ESEO/E4/S8/FichiersProjetJavaEE/"; // A changer


    public Page1() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Noms tableNoms = new Noms();
        request.setAttribute("eleves", tableNoms.recupererEleves());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String action = request.getParameter("bouton");
        System.out.println(action);
        if("Valider".equals(action)) {
            Eleve eleve = new Eleve();
            eleve.setNom(request.getParameter("Nom"));
            eleve.setPrenom(request.getParameter("Prenom"));
            eleve.setGenre(request.getParameter("genre"));
            eleve.setSitePrecedent(request.getParameter("sitePrecedent"));
            eleve.setFormationPrecedente(request.getParameter("formationPrecedente"));

            Noms tableNoms = new Noms();
            tableNoms.ajouterEleve(eleve);

            request.setAttribute("eleves", tableNoms.recupererEleves());

        }

        else if ("Valider le fichier".equals(action)) {

            // On récupère le champ description comme d'habitude
            String description = request.getParameter("description");
            request.setAttribute("description", description);

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
}
