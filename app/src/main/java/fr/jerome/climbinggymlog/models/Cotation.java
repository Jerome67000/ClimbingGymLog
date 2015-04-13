package fr.jerome.climbinggymlog.models;

import android.graphics.Color;

/**
 * Représente une Cotation
 * Created by jerome on 26/01/15.
 */
public class Cotation {

    private long id;
    private String nom;
    private int  numDiff;
    private String charDiff;
    private boolean plus;
    private int couleur = Color.BLUE;

    public Cotation(long id, String nom, int numDiff, String charDiff, boolean plus) {

        this.id = id;
        this.nom = nom;
        this.numDiff = numDiff;
        this.charDiff = charDiff;
        this.plus = plus;
    }

    /**
     * Default constructor
     */
    public Cotation() {

    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public int getNumDiff() {

        return numDiff;
    }

    public void setNumDiff(int numDiff) {

        this.numDiff = numDiff;
    }

    public String getCharDiff() {

        return charDiff;
    }

    public void setCharDiff(String charDiff) {

        this.charDiff = charDiff;
    }

    public boolean isPlus() {

        return plus;
    }

    public void setPlus(boolean plus) {

        this.plus = plus;
    }

    public int getCouleur() {

        return couleur;
    }

    public void setCouleur(int couleur) {

        this.couleur = couleur;
    }

    @Override
    public String toString() {

        return "Cotation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", numDiff=" + numDiff +
                ", charDiff='" + charDiff + '\'' +
                ", plus=" + plus +
                ", couleur=" + couleur +
                '}';
    }
}
