package miun.fl.dt142g.projekt;

import java.util.ArrayList;

public class Order {
    private ArrayList<Item> items = new ArrayList<>();
    private boolean status;
    private double orderTotal;

    // Temporary until SQL is implemented
    Order(Item ordered_dish){
        items.add(ordered_dish);
    }

}
