package miun.fl.dt142g.projekt;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String note;
    private String category;
    private double price;
    private int id;

    // Constructor for when SQL is implemented NOT COMPLETED
    Item(int dishId){
        id = dishId;
        // GET DISH WITH ID ... FROM DATABASE And set other attributes.
    }
    // Temporary constructor
    Item(String name, String note, String category, double price, int id){
        this.name = name;
        this.note = note;
        this.category = category;
        this.price = price;
        this.id = id;
    }
    String getName(){return name;}
    String getCategory(){return category;}
    double getPrice(){return price;}
    String getNote(){return note;}

    void setNote(String note){this.note = note;}
    void resetNote(){this.note = "";}
}
