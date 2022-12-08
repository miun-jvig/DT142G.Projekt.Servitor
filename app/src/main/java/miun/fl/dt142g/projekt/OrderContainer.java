package miun.fl.dt142g.projekt;

import java.io.Serializable;

import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.Order;

public class OrderContainer implements Serializable {
    private Order order;
    private Carte carte;

    public OrderContainer(Order order, Carte carte){
        this.order = order;
        this.carte = carte;
    }
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }
}
