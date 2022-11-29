package miun.fl.dt142g.projekt.json;

import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private String name;
    private Carte carte;

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
