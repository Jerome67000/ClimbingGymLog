package fr.jerome.climbinggymlog.models;

/**
 * Created by jerome on 30/01/15.
 */
public class TypeEsc {

    private long id = 0;
    private String type;

    public TypeEsc(long id, String type) {

        this.id = id;
        this.type = type;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    @Override
    public String toString() {

        return "TypeEsc{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
