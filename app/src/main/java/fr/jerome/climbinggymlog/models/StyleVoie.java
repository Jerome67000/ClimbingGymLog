package fr.jerome.climbinggymlog.models;

/**
 * Created by jerome on 30/01/15.
 */
public class StyleVoie {

    private long id = 0;
    private String Style;

    public StyleVoie(long id, String style) {

        this.id = id;
        Style = style;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getStyle() {

        return Style;
    }

    public void setStyle(String style) {

        Style = style;
    }

    @Override
    public String toString() {

        return "StyleVoie{" +
                "id=" + id +
                ", Style='" + Style + '\'' +
                '}';
    }
}
