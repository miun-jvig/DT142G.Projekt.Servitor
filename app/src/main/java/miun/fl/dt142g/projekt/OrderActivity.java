package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Table table;
    ArrayList<Item> order = new ArrayList<>();
    String chosenCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Button button_back = findViewById(R.id.button_back_order);

    // Temporary exampledishes until SQL is implemented - Will be replaced with something like getAllDishes ...
        Item tartar = new Item("Halstrad tartar", "Lättstekt", "starter", 79.90, 1);
        Item chips = new Item("Chipstalrik", "Saltade chips med dip", "starter", 59.90, 2);
        Item wings = new Item("Kycklingvingar", "Utan  het sås", "starter", 69.90, 3);
        Item curryChicken = new Item("Currykyckling", "Lite curry", "main", 109.90, 4);
        Item steak = new Item("Stek med pommes", "+Bea", "main", 159.90, 5);
        Item soup = new Item("Potatissoppa", "Med bröd att doppa", "main", 99.90, 6);
        Item carrotcake = new Item("Morotskaka", "Utan morot", "dessert", 54.90, 7);
        Item bun = new Item("Kanelbulle", "Extra socker", "dessert", 34.90, 8);
        Item cremebrulee = new Item("Créme Brûlée", "Med bär", "dessert", 109.90, 9);
        Item sprite = new Item("Sprite", "Utan is", "beverage", 29.90, 10);
        Item monster = new Item("Monster", "Sockerfri Monster", "beverage", 34.90, 11);
        Item beer = new Item("Öl", "Gärna avslagen öl", "beverage", 79.90, 12);

        Stack<Item> allItems = new Stack<>();
        allItems.add(tartar);
        allItems.add(chips);
        allItems.add(wings);
        allItems.add(curryChicken);
        allItems.add(steak);
        allItems.add(soup);
        allItems.add(carrotcake);
        allItems.add(bun);
        allItems.add(cremebrulee);
        allItems.add(sprite);
        allItems.add(monster);
        allItems.add(beer);
    //
        /*
        * If a category is selected, display only the dishes with that category
        * */
        if(getIntent().getSerializableExtra("chosenCategory") != null){
            chosenCategory = (String)getIntent().getSerializableExtra("chosenCategory");
        }
        Stack<Item> dispItems = new Stack<>();
        if(chosenCategory == null){
            dispItems = allItems;
        }
        else {
            for (Item i : allItems) {
                if(i.getCategory().equals(chosenCategory)){
                    dispItems.add(i);
                }
            }
        }
        /*
        * Adds all items, styles them accordingly & connect every button with its own item.
        * */
        TableLayout layout = findViewById(R.id.TableLayout);
        int added_items = 0;
        while(!dispItems.empty() && added_items < 15){
            Item item = dispItems.pop(); //Item to be added to the view
            TableRow row = (TableRow)layout.getChildAt((added_items)/3); //The row for the item to be added to
            Button button = (Button)row.getChildAt((added_items)%3); //The cell the item will be added to
            String text = item.getName()+"\n"+item.getPrice()+":-";

            button.setOnClickListener(v -> onItemButtonPress(item, button));
            button.setOnLongClickListener(v -> onItemHoldPress(item, button));
        // Makes the buttons visible and styles it accordingly
            button.setVisibility(View.VISIBLE);
            button.setBackgroundColor(getResources().getColor(getColorFromCategory(item.getCategory())));
            button.setText(text);
            added_items++;
        }

        // INFO ABOUT TABLE
        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = findViewById(R.id.order_current_table);
        String current_table_number = "Bord: " + table.getID();
        current_table.setText(current_table_number);

        // IF ORDER IS ALREADY STARTED
        if(getIntent().getSerializableExtra("Order") != null) {
        ArrayList<Item> oldOrder = (ArrayList<Item>) getIntent().getSerializableExtra("Order");
            order = oldOrder;
        }
        // BACK ACTIVITY
        Intent activity_booking = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(v -> startActivity(activity_booking));

        // DROPDOWN
        Spinner spinner_order = findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order.setAdapter(adapter);
        spinner_order.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = (String) parent.getItemAtPosition(pos);
        Intent activity_order = new Intent(this, OrderActivity.class);
        switch(selected){
            case "Förrätter":
                activity_order.putExtra("chosenCategory", "starter");
                activity_order.putExtra("Table", table);
                activity_order.putExtra("Order", order);
                startActivity(activity_order);
                break;
            case "Varmrätter":
                activity_order.putExtra("chosenCategory", "main");
                activity_order.putExtra("Table", table);
                activity_order.putExtra("Order", order);
                startActivity(activity_order);
                break;
            case "Efterrätter":
                activity_order.putExtra("chosenCategory", "dessert");
                activity_order.putExtra("Table", table);
                activity_order.putExtra("Order", order);
                startActivity(activity_order);
                break;
            case "Dryck":
                activity_order.putExtra("chosenCategory", "beverage");
                activity_order.putExtra("Table", table);
                activity_order.putExtra("Order", order);
                startActivity(activity_order);
                break;
            case "Beställning":
                Intent activity_sendOrder = new Intent(this, SendOrderActivity.class);
                activity_sendOrder.putExtra("Table", table);
                activity_sendOrder.putExtra("Order", order);
                startActivity(activity_sendOrder);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(getApplicationContext(),"OnNothingSelected" , Toast.LENGTH_LONG).show();
    }

    public int getColorFromCategory(String cat){
        switch(cat){
            case "starter":
                return R.color.foodYellow;
            case "main":
                return R.color.foodBlue;
            case "dessert":
                return R.color.foodPurple;
            case "beverage":
                return R.color.foodGreen;
        }
        return R.color.black;
    }

    /**
     * Creates a new VIEW containing a pop-up window for the user to write a note to an item.
     * @param item The item to add the note to.
     * @param button The button that was pressed.
     * @return returns true as event has been handled
     */
    @SuppressLint("ClickableViewAccessibility")
    public boolean onItemHoldPress(Item item, Button button) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parent = this.getWindow().findViewById(android.R.id.content);
        View popupView = inflater.inflate(R.layout.popup_add_note, (ViewGroup) parent, false);
        EditText note_user = popupView.findViewById(R.id.popup_add_note);
        TextView title_text = popupView.findViewById(R.id.note_title);
        Button button_add_note = popupView.findViewById(R.id.button_add_note);
        String title = "Notering till beställning \"" + item.getName() + "\".";

        // CREATE THE POPUP WINDOW
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        // SHOW THE POPUP WINDOW
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        // SHOW TITLE
        title_text.setText(title);

        // ON BUTTON PRESS, SET NOTE AND DISMISS VIEW
        button_add_note.setOnClickListener(v -> {
            item.setNote(note_user.getText().toString());
            popupWindow.dismiss();
        });

        // PROCEED AS REGULAR WITH onItemButtonPress
        onItemButtonPress(item, button);
        return true; // EVENT HAS BEEN HANDLED
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    public void onItemButtonPress(Item item, Button button){
        order.add(item);
        button.setBackgroundTintList(getResources().getColorStateList(R.color.appBlue));
        item.resetNote(); // RESET NOTE
    }
}