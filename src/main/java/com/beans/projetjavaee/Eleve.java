package com.beans.projetjavaee;

public class Eleve {
    private String nom;
    private String prenom;
    private String genre;
    private String sitePrecedent;
    private String formationPrecedente;
    private String equipe;

    public Eleve(String nom, String prenom, String genre, String sitePrecedent, String formationPrecedente){
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setGenre(genre);
        this.setSitePrecedent(sitePrecedent);
        this.setFormationPrecedente(formationPrecedente);
        this.setEquipe(null);

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSitePrecedent() {
        return sitePrecedent;
    }

    public void setSitePrecedent(String sitePrecedent) {
        this.sitePrecedent = sitePrecedent;
    }

    public String getFormationPrecedente() {
        return formationPrecedente;
    }

    public void setFormationPrecedente(String formationPrecedente) {
        this.formationPrecedente = formationPrecedente;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }
}
