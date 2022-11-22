package miun.fl.dt142g.projekt;
public class Item {
    private String name;
    private String description;
    private String category;
    private double price;
    private int id;

    // Constructor for when SQL is implemented NOT COMPLETED
    Item(int dishId){
        id = dishId;
        // GET DISH WITH ID ... FROM DATABASE And set other attributes.
    }
    // Temporary constructor
    Item(String name, String description, String category, double price, int id){
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.id = id;
    }
    String getName(){return name;}
    String getCategory(){return category;}
    double getPrice(){return price;}
}
