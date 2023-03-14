package com.beans.projetjavaee;

public class Eleve {
    private String nom;
    private String prenom;
    private String genre;
    private String sitePrecedent;
    private String formationPrecedente;
    private int idEquipe;

    public Eleve(){
        this.setPrenom(null);
        this.setPrenom(null);
        this.setGenre(null);
        this.setSitePrecedent(null);
        this.setFormationPrecedente(null);
    }

    public Eleve(String nom, String prenom, String genre, String sitePrecedent, String formationPrecedente){
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setGenre(genre);
        this.setSitePrecedent(sitePrecedent);
        this.setFormationPrecedente(formationPrecedente);
    }
    public String toString(){
        String message = ""+ this.getNom()+ " " + this.getPrenom() + "  " +this.getGenre() + " "
                + this.getSitePrecedent()+ " " + this.getFormationPrecedente();
        return message;
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

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }
}
