package com.beans.projetjavaee;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private String nom;
    private List<Eleve> eleves;

    public Equipe(String nom){
        this.setNom(nom);
        this.setEleves(new ArrayList<Eleve>());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }

    public void addEleve(Eleve eleve){
        eleve.setEquipe(this.getNom());
        this.getEleves().add(eleve);
    }

    public void deleteEleve(Eleve eleve){
        for(Eleve e : this.getEleves()){
            if( e.getNom() == eleve.getNom() && e.getPrenom() == eleve.getPrenom()){
                this.getEleves().remove(e);
            }
        }
    }
}
