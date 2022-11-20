package miun.fl.dt142g.projekt;

public class Order {
    private String dish;
    private String category;
    private double price;

    // TEMP UNTIL SQL IS HERE
    Order(String ordered_dish, String ordered_type, double ordered_price){
        dish = ordered_dish;
        category = ordered_type;
        price = ordered_price;
    }
}
