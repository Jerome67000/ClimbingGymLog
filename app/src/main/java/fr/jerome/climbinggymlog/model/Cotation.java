package fr.jerome.climbinggymlog.model;

/**
 * Created by jerome on 26/01/15.
 */
public class Cotation {

    private long id = 0;
    private String difficulte;

    public Cotation(long id, String difficulte) {

        this.id = id;
        this.difficulte = difficulte;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getDifficulte() {

        return difficulte;
    }

    public void setDifficulte(String difficulte) {

        this.difficulte = difficulte;
    }

    @Override
    public String toString() {

        return "Cotation{" +
                "id=" + id +
                ", difficulte='" + difficulte + '\'' +
                '}';
    }
}
