package com.beans.projetjavaee;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private String nom;
    private int id;
    private List<Eleve> eleves  = new ArrayList<>();
    public Equipe(String nom){
        this.setNom(nom);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }
}
