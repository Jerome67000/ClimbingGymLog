package fr.jerome.climbinggymlog.models;

import java.sql.Date;

/**
 * Created by jerome on 25/03/15.
 */
public class Evenement {

    private int id;
    private String titre;
    private String description;
    private Date date;
    private int heure;
    private int salleId;

    public Evenement(int id, String titre, String description, Date date, int heure, int salleId) {

        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.salleId = salleId;
    }

    public Evenement() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
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

    @Override
    public String toString() {

        return "Evenement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", salleId=" + salleId +
                '}';
    }
}
