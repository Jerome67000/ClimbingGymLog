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
