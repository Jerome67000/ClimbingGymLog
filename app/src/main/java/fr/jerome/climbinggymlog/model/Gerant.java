package fr.jerome.climbinggymlog.model;

/**
 * Created by jerome on 25/01/15.
 */
public class Gerant extends Personne {

    private boolean abonnement;
    private int idSalle;

    public Gerant(int id, String nom, String prenom, int age, boolean abonnement, int idSalle) {

        super(id, nom, prenom, age);
        this.abonnement = abonnement;
        this.idSalle = idSalle;
    }

    public Gerant(int id, String nom, String prenom, boolean abonnement, int idSalle) {

        super(id, nom, prenom);
        this.abonnement = abonnement;
        this.idSalle = idSalle;
    }

    public boolean isAbonnement() {
        return abonnement;
    }

    public void setAbonnement(boolean abonnement) {
        this.abonnement = abonnement;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    @Override
    public String toString() {

        return super.toString() + "\n" +
               "abonnement" + abonnement + "\n" +
               "id salle" + idSalle;
    }
}
