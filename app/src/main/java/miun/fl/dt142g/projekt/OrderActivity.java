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
import java.util.Collections;
import java.util.Comparator;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Table table;
    private ArrayList<Item> order = new ArrayList<>();
    private final ArrayList<Item> allItems = new ArrayList<>();

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

        // CREATE THE ORDER VIEW
        // Comparator that compares two items categories
        Comparator<? super Item> comparator = (Comparator<Item>) (item1, item2) -> {
            String category1 = item1.getCategory();
            String category2 = item2.getCategory();
            return category1.compareTo(category2);
        };
        // Sort allItems to be able to add new items, this will add it in order according to category
        Collections.sort(allItems, comparator);
        /* Creates TableRows with the length 3 (ROW_SIZE) and fill the TableRows with Buttons. The
        * buttons will have a functionality on item press to add an item to Order, and on button hold
        * add a comment to an item and then add it to Order. Also used in onItemSelected().
        */
        createTableRowTableButtons(allItems);

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

    /**
     * Creates TableRows with the length 3 (ROW_SIZE) and fill the TableRows with Buttons. The
     * buttons will have a functionality on item press to add an item to Order, and on button hold
     * add a comment to an item and then add it to Order.
     *
     * Also used in onItemSelected().
     * @param allItems List of all the items/dishes that are available. Taken from database.
     */
    public void createTableRowTableButtons(ArrayList<Item> allItems){
        TableLayout tableLayout = findViewById(R.id.table_layout);
        tableLayout.removeAllViews();

        final int ROW_SIZE = 3;
        double temp = (double) allItems.size() / ROW_SIZE;
        double columnSize = Math.ceil(temp);
        int itemCounter = 0;
        final int WIDTH = getResources().getDisplayMetrics().widthPixels/3;
        final int HEIGHT = 400;
        TableRow.LayoutParams params = new TableRow.LayoutParams(WIDTH, HEIGHT);

        for(int i = 0; i < columnSize; i++){
            // CREATE ROW
            TableRow tableRow = new TableRow(this);
            for(int j = 0; j < ROW_SIZE; j++) {
                if(itemCounter < allItems.size()) {
                    // VARIABLES
                    Item item = allItems.get(itemCounter++);
                    String text = item.getName() + "\n" + item.getPrice() + ":-";
                    int color = getResources().getColor(getColorFromCategory(item.getCategory()));
                    // CREATES BUTTON
                    Button button = new Button(this);
                    button.setText(text);
                    button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    button.setTextSize(12);
                    button.setBackgroundColor(color);
                    button.setLayoutParams(params);
                    tableRow.addView(button);

                    // ON BUTTON PRESS, ADD TO ORDER
                    button.setOnClickListener(v -> onItemButtonPress(item, button));
                    // ON BUTTON HOLD, LET USER CREATE NOTE THEN ADD TO ORDER
                    button.setOnLongClickListener(v -> onItemHoldPress(item, button));
                }
            }
            tableLayout.addView(tableRow);
        }
    }

    /**
     * Checks the value of the selected item in dropdown menu of the spinner. If "Beställning", then
     * send to activity_sendOrder. If "Vanliga rätter" then show all items in the TableLayout. If any
     * other then show that specific category only.
     * @param parent Parent of the spinner.
     * @param view Current view.
     * @param pos Position of the mouse.
     * @param id Spinner ID.
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = (String) parent.getItemAtPosition(pos);
        String category = getTypeOfFood(selected);
        Intent activity_sendOrder = new Intent(this, SendOrderActivity.class);

        switch(selected){
            case "Beställning":
                activity_sendOrder.putExtra("Table", table);
                activity_sendOrder.putExtra("Order", order);
                startActivity(activity_sendOrder);
                break;
            case "Vanliga rätter":
                createTableRowTableButtons(this.allItems);
                break;
            default:
                ArrayList<Item> itemsWithSelectedCategory = new ArrayList<>();
                for(Item i : this.allItems) {
                    String tmp = i.getCategory();
                    if (tmp.equals(category)) {
                        itemsWithSelectedCategory.add(i);
                    }
                }
                createTableRowTableButtons(itemsWithSelectedCategory);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(getApplicationContext(),"OnNothingSelected" , Toast.LENGTH_LONG).show();
    }

    /**
     * Translates from Swedish to English.
     * @param selected Selected option of the spinner dropdown menu.
     * @return The name of the category of food in English.
     */
    String getTypeOfFood(String selected){
        switch(selected){
            case "Förrätter":         return "starter";
            case "Varmrätter":        return "main";
            case "Efterrätter":       return "dessert";
            case "Dryck":             return "beverage";
        }
        return "Vanliga rätter";
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