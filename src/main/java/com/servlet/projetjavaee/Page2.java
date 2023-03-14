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
        }
        Noms tableNoms = new Noms();
        request.setAttribute("equipes", tableNoms.recupererEquipes());
        request.setAttribute("eleves", tableNoms.recupererEleves());
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
