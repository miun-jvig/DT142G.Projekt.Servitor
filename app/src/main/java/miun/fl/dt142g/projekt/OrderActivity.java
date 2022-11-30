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
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.CarteAPI;
import miun.fl.dt142g.projekt.json.Dish;
import miun.fl.dt142g.projekt.json.Employee;
import miun.fl.dt142g.projekt.json.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Booking booking;
    private Employee employee;
    private ArrayList<Order> orderList = new ArrayList<>();
    private final ArrayList<Carte> allItems = new ArrayList<>();
    public final Dish dish = new Dish();
    public final Carte carte = new Carte();
    public final Dish dish2 = new Dish();
    public final Carte carte2 = new Carte();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Button buttonBack = findViewById(R.id.button_back_order);
        employee = (Employee) getIntent().getSerializableExtra("Employee");

        CarteAPI carteAPI = APIClient.getClient().create(CarteAPI.class);
        Call<List<Carte>> call = carteAPI.getAllCarte();
        call.enqueue(new Callback<List<Carte>>() {
            @Override
            public void onResponse(Call<List<Carte>> call, Response<List<Carte>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Helvete!" , Toast.LENGTH_LONG).show();
                    return;
                }
                List<Carte> carte = response.body();
                if(!carte.isEmpty()) {
                    for(Carte c : carte){
                        allItems.add(c);
                    }
                    Comparator<? super Carte> comparator = (Comparator<Carte>) (item1, item2) -> {
                        String category1 = item1.getCategory();
                        String category2 = item2.getCategory();
                        return category1.compareTo(category2);
                    };
                    // Sort allItems to be able to add new items, this will add it in order according to category
                    Collections.sort(allItems, comparator);
                    createTableRowTableButtons(allItems);
                }
            }
            @Override
            public void onFailure(Call<List<Carte>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network error." , Toast.LENGTH_LONG).show();


            }
        });


        //Creates TableRows with the length 3 (ROW_SIZE) and fill the TableRows with Buttons. The
        /* buttons will have a functionality on item press to add an item to Order, and on button hold
        * add a comment to an item and then add it to Order. Also used in onItemSelected().
        */

        // INFO ABOUT TABLE
        booking = (Booking) getIntent().getSerializableExtra("Booking");
        TextView currentTable = findViewById(R.id.order_current_table);
        String currentTableNumber = "Bord: " + booking.getTableNumber();
        currentTable.setText(currentTableNumber);

        // IF ORDER IS ALREADY STARTED
        if(getIntent().getSerializableExtra("Order") != null) {
            orderList = (ArrayList<Order>) getIntent().getSerializableExtra("Order");
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
    public void createTableRowTableButtons(ArrayList<Carte> allItems){
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
        final int WIDTH = getResources().getDisplayMetrics().widthPixels/ROW_SIZE;
        final int HEIGHT = WIDTH;
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
                    Carte item = allItems.get(itemCounter++);
                    String text = item.getDish().getName() + "\n" + item.getPrice() + ":-";
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
                activitySendOrder.putExtra("Order", orderList);
                activitySendOrder.putExtra("Booking", booking);
                startActivity(activitySendOrder);
                break;
            case "Vanliga rätter":
                createTableRowTableButtons(this.allItems);
                break;
            default:
                ArrayList<Carte> itemsWithSelectedCategory = new ArrayList<>();
                for(Carte i : this.allItems) {
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
            case "Förrätter":         return "Förrätt";
            case "Varmrätter":        return "Varmrätt";
            case "Efterrätter":       return "Efterrätt";
            case "Dryck":             return "Dryck";
        }
        return "Vanliga rätter";
    }

    public int getColorFromCategory(String cat){
        switch(cat){
            case "Förrätt":            return R.color.foodYellow;
            case "Varmrätt":               return R.color.foodBlue;
            case "Efterrätt":            return R.color.foodPurple;
            case "Dryck":           return R.color.foodGreen;
        }
        return R.color.appRed;
    }

    /**
     * Creates a new VIEW containing a pop-up window for the user to write a note to an item.
     * @param item The item to add the note to.
     * @param button The button that was pressed.
     * @return returns true as event has been handled
     */
    @SuppressLint("ClickableViewAccessibility")
    public boolean onItemHoldPress(Carte item, Button button) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parent = this.getWindow().findViewById(android.R.id.content);
        View popupView = inflater.inflate(R.layout.popup_add_note, (ViewGroup) parent, false);
        EditText noteUser = popupView.findViewById(R.id.popup_add_note);
        TextView titleText = popupView.findViewById(R.id.note_title);
        Button buttonAddNote = popupView.findViewById(R.id.button_add_note);
        String title = "Notering till beställning \"" + item.getDish().getName() + "\".";
        Order order = createOrder(item);

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
            order.setNote(noteUser.getText().toString());
            popupWindow.dismiss();
        });

        // PROCEED AS REGULAR WITH ADD TO orderList
        orderList.add(order);
        button.setBackgroundTintList(getResources().getColorStateList(R.color.appBlue));
        return true; // EVENT HAS BEEN HANDLED
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    public void onItemButtonPress(Carte item, Button button){
        Order order = createOrder(item);
        orderList.add(order);
        button.setBackgroundTintList(getResources().getColorStateList(R.color.appBlue));
    }

    public Order createOrder(Carte item){
        Order order = new Order();
        order.setEmployee(employee);
        order.setBooking(booking);
        order.setStatus(false);
        order.setDish(item.getDish());
        return order;
    }
}