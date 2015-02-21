package fr.jerome.climbinggymlog.model;

import android.graphics.Bitmap;

/**
 * Created by jerome on 25/01/15.
 */
public class Voie {

    public static enum Style {Dalle, Verticale, LegerDevers, grosDevers, Toit, Bloc}
    public static enum TYPE_ESC {Dalle, Verticale, LegerDevers, grosDevers, Toit, Bloc}

    private long id = 0;
    private long idSeance;
    private String nom;
    private Cotation cotation;
    private TypeEsc typeEscalade; //enum ?
    private StyleVoie style; //enum ?
    private boolean reussi;
    private boolean aVue;
    private String note;
    private Bitmap photo;

    public Voie() {

    }

    public Voie(long id, long idSeance, String nom, Cotation cotation, TypeEsc typeEscalade, StyleVoie style, boolean reussi, boolean aVue, String note, Bitmap photo) {

        this.id = id;
        this.idSeance = idSeance;
        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.style = style;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.photo = photo;
    }

    public Voie(long idSeance, String nom, Cotation cotation, TypeEsc typeEscalade, StyleVoie style, boolean reussi, boolean aVue, String note, Bitmap photo) {

        this.idSeance = idSeance;
        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.style = style;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.photo = photo;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public long getIdSeance() {

        return idSeance;
    }

    public void setIdSeance(long idSeance) {

        this.idSeance = idSeance;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public Cotation getCotation() {

        return cotation;
    }

    public void setCotation(Cotation cotation) {

        this.cotation = cotation;
    }

    public TypeEsc getTypeEscalade() {

        return typeEscalade;
    }

    public void setTypeEscalade(TypeEsc typeEscalade) {

        this.typeEscalade = typeEscalade;
    }

    public StyleVoie getStyle() {

        return style;
    }

    public void setStyle(StyleVoie style) {

        this.style = style;
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
                "style voie : " + style + "\n" +
                "réussi ? " + reussi + "\n" +
                "à vue ? " + aVue + "\n" +
                "note : " + note + "\n";
    }
}
