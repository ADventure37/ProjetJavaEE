package com.servlet.projetjavaee;

import com.bdd.projetjavaee.Noms;
import com.beans.projetjavaee.Eleve;
import com.beans.projetjavaee.Equipe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "Page2", value = "/Page2")
public class Page2 extends HttpServlet{
    private static final long serialVersionUID = 1L;


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
        } else if (action.equals("Génération automatique")) {
            Noms tableNoms = new Noms();
            List<Eleve> eleves = tableNoms.recupererElevesS();
            List<Equipe> equipes = tableNoms.recupererEquipes();
            int nbEq;
            if (eleves.size()>0) {
                if (equipes.get(equipes.size() - 1).getEleves().size() == 2) {
                    nbEq = eleves.size() / 2 + 1;
                    int nb = tableNoms.recupererEquipes().size();
                    createXEquipe("" + nbEq);
                    for (int i = nb+1; i < eleves.size(); i++) {
                        int numEq = i / 2 + 1;
                        tableNoms.ajouterAEquipe(numEq, eleves.get(i));
                    }
                }else if (equipes.get(equipes.size() - 1).getEleves().size() < 2){
                    nbEq = eleves.size() / 2 ;
                    int nb = tableNoms.recupererEquipes().size();
                    createXEquipe("" + nbEq);
                    for (int i = nb; i < eleves.size(); i++) {
                        int numEq = i / 2 + 1;
                        tableNoms.ajouterAEquipe(numEq, eleves.get(i));
                }
            }
            }

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

}
