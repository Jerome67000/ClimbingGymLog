package fr.jerome.climbinggymlog.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jerome on 25/01/15.
 */
public class Client extends Personne {

    private int numClient;
    private Date dateAjout;
    private int idSalle;
    private ArrayList<Seance> seances;

    public Client(int id, String nom, String prenom, int age, int numClient, Date dateAjout, int idSalle) {

        super(id, nom, prenom, age);
        this.numClient = numClient;
        this.dateAjout = dateAjout;
        this.idSalle = idSalle;
        this.seances = new ArrayList<Seance>();
    }

    public Client(int id, String nom, String prenom, int numClient, Date dateAjout, int idSalle) {

        super(id, nom, prenom);
        this.numClient = numClient;
        this.dateAjout = dateAjout;
        this.idSalle = idSalle;
        this.seances = new ArrayList<Seance>();
    }

    public void addSeance(Seance newSeance) {
        seances.add(newSeance);
    }

    public int getNumClient() {

        return numClient;
    }

    public void setNumClient(int numClient) {

        this.numClient = numClient;
    }

    public Date getDateAjout() {

        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {

        this.dateAjout = dateAjout;
    }

    public int getIdSalle() {

        return idSalle;
    }

    public void setIdSalle(int idSalle) {

        this.idSalle = idSalle;
    }

    public ArrayList<Seance> getSeances() {

        return seances;
    }

    public void setSeances(ArrayList<Seance> seances) {

        this.seances = seances;
    }

    @Override
    public String toString() {

        return super.toString() + "\n" +
               "num Client : " + numClient + "\n" +
               "date Ajout : " + dateAjout + "\n" +
               "id Salle : " + idSalle + "\n" +
               "nombre de séances : " + seances.size();
    }
}
