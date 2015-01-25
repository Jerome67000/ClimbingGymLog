package fr.jerome.climbinggymlog.model;

/**
 * Created by jerome on 25/01/15.
 */
public class Adresse {

    private int id;
    private int num;
    private String rue;
    private int cp;
    private String ville;

    /**
     * Représente une adresse physique
     */
    public Adresse(int id, int num, String rue, int cp, String ville) {

        this.id = id;
        this.num = num;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
    }

    /**
     * Default constructor
     */
    public Adresse() {

    }

    public int getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(short cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {

        return  "_id" + id + "\n" +
                "numéro : " + num + "\n" +
                "rue : " + rue + "\n" +
                "code postale : " + cp + "\n" +
                "ville : " + ville;
    }
}
