package miun.fl.dt142g.projekt;

import java.io.Serializable;
import java.util.ArrayList;

public class Booking implements Serializable {
    private String note;
    private String servitor;
    private ArrayList<Order> orders = new ArrayList<>();
    private int total;

    Booking(String note, String servitor){
        this.note = note;
        this.servitor = servitor;
    }

    public void addOrderItem(Order order){
        orders.add(order);
    }
    public ArrayList<Order> getOrders(){
        return orders;
    }
}
