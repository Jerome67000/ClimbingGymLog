package fr.jerome.climbinggymlog.models;

import android.graphics.Bitmap;

/**
 * Created by jerome on 25/01/15.
 */
public class Voie {

    private long id = 0;
    private String nom;
    private Cotation cotation;
    private TypeEsc typeEscalade;
    private StyleVoie style;
    private boolean reussi;
    private boolean aVue;
    private String note;
    private long idSeance;
    private Bitmap photo;

    public Voie() {

    }

    public Voie(long id, String nom, Cotation cotation, TypeEsc typeEscalade, StyleVoie style, boolean reussi, boolean aVue, String note, long idSeance) {

        this.id = id;
        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.style = style;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.idSeance = idSeance;
    }

    public Voie(String nom, Cotation cotation, TypeEsc typeEscalade, StyleVoie style, boolean reussi, boolean aVue, String note, long idSeance) {

        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.style = style;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.idSeance = idSeance;
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

        return "Voie{" + '\'' +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", cotation=" + cotation +
                ", typeEscalade=" + typeEscalade +
                ", style=" + style +
                ", reussi=" + reussi +
                ", aVue=" + aVue +
                ", note='" + note + '\'' +
                ", idSeance=" + idSeance +
                ", photo=" + photo +
                '}';
    }
}
