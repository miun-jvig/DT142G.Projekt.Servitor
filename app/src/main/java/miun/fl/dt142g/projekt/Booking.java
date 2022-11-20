package miun.fl.dt142g.projekt;

import java.util.ArrayList;

public class Booking {
    private String note;
    private String servitor;
    private Table table;
    private ArrayList<Order> orders = new ArrayList<>();

    Booking(Table booking_table, String booking_note, String booking_servitor){
        table = booking_table;
        note = booking_note;
        servitor = booking_servitor;
    }

    public int getTableID(){
        return table.getID();
    }
    public boolean getTableStatus(){
        return table.getStatus();
    }
    public void addOrderItem(Order order){
        orders.add(order);
    }
    public ArrayList<Order> getOrders(){
        return orders;
    }
}
