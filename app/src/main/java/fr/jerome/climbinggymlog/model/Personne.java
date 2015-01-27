package fr.jerome.climbinggymlog.model;

/**
 * Created by jerome on 25/01/15.
 */
public abstract class Personne {

    protected int id;
    protected String nom;
    protected String prenom;
    protected int age = 0;

    public Personne(int id, String nom, String prenom, int age) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }
    public Personne(int id, String nom, String prenom) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getPrenom() {

        return prenom;
    }

    public void setPrenom(String prenom) {

        this.prenom = prenom;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    @Override
    public String toString() {

        return  "_id" + id + "\n" +
                "nom : " + nom + "\n" +
                "prenom : " + prenom + "\n" +
                "age : " + age;
    }
}
