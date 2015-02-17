package fr.jerome.climbinggymlog.model;

import java.sql.Date;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.database.ClientDB;

/**
 * Created by jerome on 25/01/15.
 */
public class Client extends Personne {

    private String numClient;
    private Date dateAjout;
    private long idSalle;
    private String nomSalle;
    private ArrayList<Seance> seances;

    /**
     * Constructor for re create a Client
     *
     * @param id du client dans la DB
     * @param nom du client
     * @param prenom du client
     * @param age du client
     * @param numClient du client (id du client dans la SAE)
     * @param dateAjout du client dans la DB
     * @param idSalle du client (salle associée au client)
     */
    public Client(long id, String nom, String prenom, int age, String numClient, Date dateAjout, long idSalle) {

        super(id, nom, prenom, age);
        this.numClient = numClient;
        this.dateAjout = dateAjout;
        this.idSalle = idSalle;
        this.seances = new ArrayList<Seance>();
    }

    /**
     * Constructor for a new Client, id generated by the DB
     *
     * @param nom du client
     * @param prenom du client
     * @param age du client
     * @param numClient du client (id du client dans la SAE)
     * @param dateAjout du client dans la DB
     * @param idSalle du client (salle associée au client)
     */
    public Client(String nom, String prenom, int age, String numClient, Date dateAjout, int idSalle) {

        super(nom, prenom, age);
        this.numClient = numClient;
        this.dateAjout = dateAjout;
        this.idSalle = idSalle;
        this.seances = new ArrayList<Seance>();
    }

    /**
     * Default constructor for a Client
     */
    public Client() {

    }

    /**
     * Add a séance to the client
     * @param newSeance
     */
    public void addSeance(Seance newSeance) {
        seances.add(newSeance);
    }

    public String getNumClient() {

        return numClient;
    }

    public void setNumClient(String numClient) {

        this.numClient = numClient;
    }

    public Date getDateAjout() {

        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {

        this.dateAjout = dateAjout;
    }

    public long getIdSalle() {

        return idSalle;
    }

    public void setIdSalle(long idSalle) {

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
