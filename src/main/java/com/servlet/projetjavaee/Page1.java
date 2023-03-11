package com.servlet.projetjavaee;

import com.beans.projetjavaee.Eleve;
import com.dtb.ConnectMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "Page1", value = "/Page1")
public class Page1 extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private List<Eleve> eleves = new ArrayList<Eleve>();

    public Page1() {
        super();
        for (int i = 0; i<5; i++){
            eleves.add(new Eleve("Deloy", "Adrien", "Homme", "Angers", "MPSI"));
        }
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
            System.out.println("2");
            String nom = request.getParameter("Nom");
            String prenom = request.getParameter("Prenom");
            String genre = request.getParameter("genre");
            String site = request.getParameter("sitePrecedent");
            String formation = request.getParameter("formationPrecedente");
            eleves.add(new Eleve(nom, prenom, genre, site, formation));
            request.getSession().setAttribute("eleves", eleves);
        }
        System.out.println("3");
        this.getServletContext().getRequestDispatcher("/WEB-INF/Page1.jsp").forward(request, response);
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }
}
