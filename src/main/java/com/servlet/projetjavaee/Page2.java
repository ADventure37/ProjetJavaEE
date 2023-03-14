package com.servlet.projetjavaee;

import com.bdd.projetjavaee.Noms;
import com.beans.projetjavaee.Eleve;
import com.beans.projetjavaee.Equipe;
import com.opencsv.CSVWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "Page2", value = "/Page2")
public class Page2 extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public static final String CHEMIN_FICHIERS = "D:/ESEO/E4/S8/FichiersProjetJavaEE/"; // A changer

    public Page2() {

        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Noms tableNoms = new Noms();
        request.setAttribute("equipes", tableNoms.recupererEquipes());
        request.setAttribute("elevesSeul", tableNoms.recupererElevesS());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page2.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String action = request.getParameter("bouton");
        if(action.equals("Créer les équipes")){
            String nbEquipe = request.getParameter("Nombre");
            createXEquipe(nbEquipe);


        } else if (action.equals("Modifier le nom d'équipe")) {
            String ancien = request.getParameter("ChangerN");
            String nouveau = request.getParameter("Nouveau Nom");
            Noms tableNoms = new Noms();
            tableNoms.modifNomEq(ancien, nouveau);

        } else if (action.equals("Valider l'assignation")) {
            String equipe = request.getParameter("AssignerEq");
            String eleve = request.getParameter("AssignerEl");
            String[] split = eleve.split(" ");
            String nom = split[0];
            String prenom = split[1];
            Noms tableNoms = new Noms();
            for(Equipe e: tableNoms.recupererEquipes()){
                if(e.getNom().equals(equipe)){
                    for(Eleve el : tableNoms.recupererElevesS()){
                        if(el.getNom().equals(nom) && el.getPrenom().equals(prenom)){
                            tableNoms.ajouterAEquipe(e.getId(), el);
                        }
                    }
                }
            }

        } else if (action.equals("Valider la suppression d'élève(s)")) {
            String eleve = request.getParameter("SupprimerEl");

            String[] split = eleve.split(" ");
            String nom = split[0];
            String prenom = split[1];

            Eleve el = new Eleve();
            el.setNom(nom);
            el.setPrenom(prenom);

            Noms tableNoms = new Noms();
            tableNoms.supprimerEl(el);

        } else if (action.equals("Récupérer le fichier")) {
            writeCsv("fichier_csv");
        }
        Noms tableNoms = new Noms();
        request.setAttribute("equipes", tableNoms.recupererEquipes());
        request.setAttribute("elevesSeul", tableNoms.recupererElevesS());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page2.jsp").forward(request, response);
    }

    private void createXEquipe(String x){
        try{
            Noms tableNoms = new Noms();
            int nombre = Integer.parseInt(x);
            int nb = tableNoms.recupererEquipes().size();
            for(int i=nb+1; i< nombre+ nb+1; i++){
                tableNoms.ajouterEquipe(new Equipe("Equipe " + i));
            }
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
    }

    private void writeCsv(String nomFichier) {
        File file = new File(CHEMIN_FICHIERS + nomFichier);

        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Name", "Class", "Marks" });
            data.add(new String[] { "Aman", "10", "620" });
            data.add(new String[] { "Suraj", "10", "630" });
            writer.writeAll(data);

            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
