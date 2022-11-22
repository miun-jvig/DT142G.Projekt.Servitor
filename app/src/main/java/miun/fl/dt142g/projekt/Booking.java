package miun.fl.dt142g.projekt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Booking implements Serializable {
    private String note;
    private String name;
    private Date time;
    private String amount;
    private ArrayList<Order> orders = new ArrayList<>();

    Booking(String name, String amount, String note){
        this.note = note;
        this.name = name;
//        this.time = time;
        this.amount = amount;
    }

    public void addOrderItem(Order order){
        orders.add(order);
    }
    public ArrayList<Order> getOrders(){
        return orders;
    }
}
