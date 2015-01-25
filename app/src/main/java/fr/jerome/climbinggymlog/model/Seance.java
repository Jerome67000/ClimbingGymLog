package fr.jerome.climbinggymlog.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jerome on 25/01/15.
 */
public class Seance {

    private int id;
    private String nom;
    private Date dateAjout;
    private String nomSalle;
    private String note;
    private int idClient;
    private ArrayList<Voie> voies;

    public Seance(int id, String nom, Date dateAjout, String nomSalle, String note, int idClient) {

        this.id = id;
        this.nom = nom;
        this.dateAjout = dateAjout;
        this.nomSalle = nomSalle;
        this.note = note;
        this.idClient = idClient;
        this.voies = new ArrayList<Voie>();
    }

    public void addVoie(Voie newVoie) {

        voies.add(newVoie);
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public Date getDateAjout() {

        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {

        this.dateAjout = dateAjout;
    }

    public String getNomSalle() {

        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {

        this.nomSalle = nomSalle;
    }

    public String getNote() {

        return note;
    }

    public void setNote(String note) {

        this.note = note;
    }

    public int getIdClient() {

        return idClient;
    }

    public void setIdClient(int idClient) {

        this.idClient = idClient;
    }

    public ArrayList<Voie> getVoies() {

        return voies;
    }

    public void setVoies(ArrayList<Voie> voies) {

        this.voies = voies;
    }

    @Override
    public String toString() {

        return super.toString();
    }
}
