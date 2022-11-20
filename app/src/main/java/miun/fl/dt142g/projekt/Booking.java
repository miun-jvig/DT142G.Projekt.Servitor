package miun.fl.dt142g.projekt;

import java.util.ArrayList;

public class Booking {
    private String note;
    private String servitor;
    private ArrayList<Order> orders = new ArrayList<>();

    Booking(String booking_note, String booking_servitor){
        note = booking_note;
        servitor = booking_servitor;
    }

    public void addOrderItem(Order order){
        orders.add(order);
    }
    public ArrayList<Order> getOrders(){
        return orders;
    }
}
