package miun.fl.dt142g.projekt;

import java.util.ArrayList;

public class Order {
    private ArrayList<Item> dishes = new ArrayList<>();
    private boolean status;
    private double orderTotal;

    // Temporary until SQL is implemented
    Order(Item ordered_dish){
        dishes.add(ordered_dish);
    }

}
