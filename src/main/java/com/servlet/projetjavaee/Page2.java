package com.servlet.projetjavaee;

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
    private List<Equipe> equipes = new ArrayList<Equipe>();
    private List<Eleve> eleves = new ArrayList<Eleve>();


    public Page2() {

        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.getSession().setAttribute("eleves", eleves);
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
            for(Equipe e :equipes){
                if(e.getNom().equals(ancien.trim())){
                    e.setNom(nouveau);
                }
            }
        } else if (action.equals("Valider l'assignation")) {
            String equipe = request.getParameter("AssignerEq");
            String eleve = request.getParameter("AssignerEl");
        }
        request.setAttribute("equipes", equipes);
        request.getSession().setAttribute("eleves", eleves);
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page2.jsp").forward(request, response);
    }

    private void createXEquipe(String x){
        try{
            int nombre = Integer.parseInt(x);
            int nb = equipes.size();
            for(int i=nb+1; i< nombre+ nb+1; i++){
                equipes.add(new Equipe(i,"Equipe " + i));
            }
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }
}
