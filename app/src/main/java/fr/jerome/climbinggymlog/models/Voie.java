package fr.jerome.climbinggymlog.models;

import android.graphics.Bitmap;

/**
 * Created by jerome on 25/01/15.
 */
public class Voie {

    private int id;
    private String nom;
    private Cotation cotation;
    private TypeEsc typeEscalade;
    private StyleVoie style;
    private boolean reussi;
    private boolean aVue;
    private String note;
    private int seanceId;
    private int clientId;
    private Bitmap photo;

    public Voie() {

    }

    public Voie(int id, String nom, Cotation cotation, TypeEsc typeEscalade, StyleVoie style, boolean reussi, boolean aVue, String note, int seanceId, int clientId) {

        this.id = id;
        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.style = style;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.seanceId = seanceId;
        this.clientId = clientId;
    }

    public Voie(String nom, Cotation cotation, TypeEsc typeEscalade, StyleVoie style, boolean reussi, boolean aVue, String note, int seanceId, int clientId) {

        this.nom = nom;
        this.cotation = cotation;
        this.typeEscalade = typeEscalade;
        this.style = style;
        this.reussi = reussi;
        this.aVue = aVue;
        this.note = note;
        this.seanceId = seanceId;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(int seanceId) {
        this.seanceId = seanceId;
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

    public long getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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
                ", seanceId=" + seanceId +
                ", photo=" + photo +
                '}';
    }
}
