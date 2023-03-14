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
        //Définition de l'attribut de la requête qui correspond à la liste des équipes
        request.setAttribute("equipes", tableNoms.recupererEquipes());
        //Définition de l'attribut de la requête qui correspond à la liste des élèves sans équipe
        request.setAttribute("elevesSeul", tableNoms.recupererElevesS());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page2.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String action = request.getParameter("bouton");
        //Permet la création d'un certain nombre d'équipes
        if(action.equals("Créer les équipes")){
            String nbEquipe = request.getParameter("Nombre");
            createXEquipe(nbEquipe);

        // Permet la modification d'un nom d'équipe
        } else if (action.equals("Modifier le nom d'équipe")) {
            String ancien = request.getParameter("ChangerN");
            String nouveau = request.getParameter("Nouveau Nom");
            Noms tableNoms = new Noms();
            tableNoms.modifNomEq(ancien, nouveau);

        //Permet l'assignation des élèves dans des équipes
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

        //Permet l'assignation automatique des élèves dans des équipes
        } else if (action.equals("Génération automatique")) {
            Noms tableNoms = new Noms();
            List<Eleve> eleves = tableNoms.recupererEleves();
            List<Equipe> equipes = tableNoms.recupererEquipes();
            int nbEquipe = equipes.size();
            int nbEleve = eleves.size();
            if(nbEleve<nbEquipe){
                nbEquipe = nbEleve;
            }
            int div = nbEleve/nbEquipe;
            double rest = nbEleve%nbEquipe;
            for(int i =1;i<=nbEquipe;i++){
                for(int j = 0; j<nbEleve;j++){
                    if(j/div < i && j/div >= i-1){
                        tableNoms.ajouterAEquipe(i, eleves.get(j));
                    }
                }
            }
            if(rest!=0) {
                int val1 = (int) rest;
                for (int j = 0; j < val1; j++) {
                    Eleve e = eleves.get(j+div*nbEquipe);
                    int equipe = j+1;
                    tableNoms.ajouterAEquipe(equipe, e);
                }
            }
        //Enclanche la supression d'un élève d'une équipe
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
        //Définition de l'attribut de la requête qui correspond à la liste des équipes
        request.setAttribute("equipes", tableNoms.recupererEquipes());
        //Définition de l'attribut de la requête qui correspond à la liste des élèves sans équipe
        request.setAttribute("elevesSeul", tableNoms.recupererElevesS());
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page2.jsp").forward(request, response);
    }

    //Créé un nombre x d'équipe
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
