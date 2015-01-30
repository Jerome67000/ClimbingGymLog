package fr.jerome.climbinggymlog.model;

/**
 * Created by jerome on 26/01/15.
 */
public class Cotation {

    private int id = 0;
    private String difficulte;

    public Cotation(String difficulte) {

        this.difficulte = difficulte;
    }

    public Cotation(int id, String difficulte) {

        this.id = id;
        this.difficulte = difficulte;
    }

    public String getDifficulte() {

        return difficulte;
    }

    public void setDifficulte(String difficulte) {

        this.difficulte = difficulte;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    @Override
    public String toString() {

        return "Cotation{" +
                "id=" + id +
                ", difficulte='" + difficulte + '\'' +
                '}';
    }
}
