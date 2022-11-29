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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.CarteAPI;
import miun.fl.dt142g.projekt.json.Employee;
import miun.fl.dt142g.projekt.json.EmployeeAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Table table;
    private ArrayList<Item> order = new ArrayList<>();
    private final ArrayList<Item> allItems = new ArrayList<>();
    //public final Order order = new Order();
    // public final Dish dish = new Dish();
    // public final CarteItem c = new CarteItem()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Button buttonBack = findViewById(R.id.button_back_order);

        String samuel = "10.82.231.15";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + samuel + ":8080/antons-skafferi-db-1.0-SNAPSHOT/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarteAPI carteAPI = retrofit.create(CarteAPI.class);
        Call<List<Carte>> call = carteAPI.getAllCarte();
        call.enqueue(new Callback<List<Carte>>() {
             @Override
             public void onResponse(Call<List<Carte>> call, Response<List<Carte>> response) {
                 if(!response.isSuccessful()) {
                     // dåligt svar
                     return;
                 }
                 List<Carte> carte = response.body();
                for(Carte c : carte){

                }
             }
            @Override
            public void onFailure(Call<List<Carte>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network error." , Toast.LENGTH_LONG).show();
            }
        });

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
        TextView currentTable = findViewById(R.id.order_current_table);
        String currentTableNumber = "Bord: " + table.getID();
        currentTable.setText(currentTableNumber);

        // IF ORDER IS ALREADY STARTED
        if(getIntent().getSerializableExtra("Order") != null) {
            ArrayList<Item> oldOrder = (ArrayList<Item>) getIntent().getSerializableExtra("Order");
            order = oldOrder;
        }
        // BACK ACTIVITY
        Intent activityBooking = new Intent(this, TablesActivity.class);
        buttonBack.setOnClickListener(v -> startActivity(activityBooking));

        // DROPDOWN
        Spinner spinnerOrder = findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrder.setAdapter(adapter);
        spinnerOrder.setOnItemSelectedListener(this);
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
        // REMOVES ALL BUTTONS ON CALL TO MAKE DROPDOWN MENU WORK CORRECT
        tableLayout.removeAllViews();

        final int ROW_SIZE = 3;
        double temp = (double) allItems.size() / ROW_SIZE;
        /* columnSize uses Math.ceil in case value of temp is a non-integer. In this case will
        *  round up to create an additional row.
        */
        final double COLUMN_SIZE = Math.ceil(temp);
        int itemCounter = 0;
        // WIDTH = THREE ITEMS
        final int WIDTH = getResources().getDisplayMetrics().widthPixels/3;
        final int HEIGHT = 400;
        final int TEXT_SIZE = 12;
        // PARAMETERS FOR THE Button
        TableRow.LayoutParams params = new TableRow.LayoutParams(WIDTH, HEIGHT);

        // CREATE ROW
        for(int i = 0; i < COLUMN_SIZE; i++){
            TableRow tableRow = new TableRow(this);
            // CREATE BUTTONS IN ROW
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
                    button.setTextSize(TEXT_SIZE);
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
        Intent activitySendOrder = new Intent(this, SendOrderActivity.class);

        switch(selected){
            case "Beställning":
                activitySendOrder.putExtra("Table", table);
                activitySendOrder.putExtra("Order", order);
                startActivity(activitySendOrder);
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
                break;
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
            case "starter":            return R.color.foodYellow;
            case "main":               return R.color.foodBlue;
            case "dessert":            return R.color.foodPurple;
            case "beverage":           return R.color.foodGreen;
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
        EditText noteUser = popupView.findViewById(R.id.popup_add_note);
        TextView titleText = popupView.findViewById(R.id.note_title);
        Button buttonAddNote = popupView.findViewById(R.id.button_add_note);
        String title = "Notering till beställning \"" + item.getName() + "\".";

        // CREATE THE POPUP WINDOW
        final int WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
        final int HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, WIDTH, HEIGHT, true);

        // SHOW THE POPUP WINDOW
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        // SHOW TITLE
        titleText.setText(title);

        // ON BUTTON PRESS, SET NOTE AND DISMISS VIEW
        buttonAddNote.setOnClickListener(v -> {
            item.setNote(noteUser.getText().toString());
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