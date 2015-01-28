package fr.jerome.climbinggymlog.model;

import android.graphics.Bitmap;

/**
 * Created by jerome on 25/01/15.
 */
public class Voie {

    private int id;
    private int idSeance;
    private String nom;
    private String cotation; //enum ?
    private String typeEscalade; //enum ?
    private String styleVoie; //enum ?
    private boolean reussi;
    private boolean aVue;
    private String note;
    private Bitmap photo;

    public Voie(int id, int idSeance, String nom, String cotation, String typeEscalade, String styleVoie, boolean reussi, boolean aVue, String note, Bitmap photo) {

        this.id = id;
        this.idSeance = idSeance;
        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.styleVoie = styleVoie;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.photo = photo;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getIdSeance() {

        return idSeance;
    }

    public void setIdSeance(int idSeance) {

        this.idSeance = idSeance;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getCotation() {

        return cotation;
    }

    public void setCotation(String cotation) {

        this.cotation = cotation;
    }

    public String getTypeEscalade() {

        return typeEscalade;
    }

    public void setTypeEscalade(String typeEscalade) {

        this.typeEscalade = typeEscalade;
    }

    public String getStyleVoie() {

        return styleVoie;
    }

    public void setStyleVoie(String styleVoie) {

        this.styleVoie = styleVoie;
    }

    public boolean isReussi() {

        return reussi;
    }

    public void setReussi(boolean reussi) {

        this.reussi = reussi;
    }

    public boolean isAVue() {

        return aVue;
    }

    public void setaVue(boolean aVue) {

        this.aVue = aVue;
    }

    public String getNote() {

        return note;
    }

    public void setNote(String note) {

        this.note = note;
    }

    public Bitmap getPhoto() {

        return photo;
    }

    public void setPhoto(Bitmap photo) {

        this.photo = photo;
    }

    @Override
    public String toString() {

        return  "_id" + id + "\n" +
                "id séance" + idSeance + "\n" +
                "nom : " + nom + "\n" +
                "cotation : " + cotation + "\n" +
                "type d'escalade : " + typeEscalade + "\n" +
                "style voie : " + styleVoie+ "\n" +
                "réussi ? " + reussi + "\n" +
                "à vue ? " + aVue + "\n" +
                "note : " + note + "\n";
    }
}
