package fr.jerome.climbinggymlog.models;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by jerome on 25/03/15.
 */
public class Evenement {

    private int _id;
    private String titre;
    private String description;
    private Date date;
    private int heure;
    private int salleId;

    public Evenement(int _id, String titre, String description, Date date, int heure, int salleId) {

        this._id = _id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.salleId = salleId;
    }

    public Evenement() {

    }

    public int get_id() {

        return _id;
    }

    public void set_id(int _id) {

        this._id = _id;
    }

    public String getTitre() {

        return titre;
    }

    public void setTitre(String titre) {

        this.titre = titre;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public int getHeure() {

        return heure;
    }

    public void setHeure(int heure) {

        this.heure = heure;
    }

    public int getSalleId() {

        return salleId;
    }

    public void setSalleId(int salleId) {

        this.salleId = salleId;
    }
}
