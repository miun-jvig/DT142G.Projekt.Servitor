package miun.fl.dt142g.projekt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.BookingAPI;
import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablesActivity extends AppCompatActivity {
    private final ArrayList<Booking> allBookingsWithDate = new ArrayList<>(); // all bookings for the date to be added to this
    private TextView editDate;
    private String dateText;
    private int mYear, mMonth, mDay;
    private static final int NOTIFICATION_ID = 1;
    private final Vector<Integer> tableNumbers = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        // fill the vector tableNumbers
        for (int i = 1; i <= 7; i++){
            tableNumbers.add(i);
        }

        // DATE VIEW________________________
        editDate = findViewById(R.id.date_choice); // the chosen date
        // Get and set Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateText = mYear + "-" + (mMonth + 1) + "-" + mDay;
        editDate.setText(dateText);

        createListOfBookings(dateText);

        // Change the date
        editDate.setOnClickListener(view -> {
            // PICK A DATE
            DatePickerDialog datePickerDialog = new DatePickerDialog(TablesActivity.this,
                    (datePicker, year, month, day) -> {
                        String dateTText = year + "-" + (month + 1) + "-" + day;
                        editDate.setText(dateTText);
                        dateText = dateTText;
                        createListOfBookings(dateTText);
                    }, mYear, mMonth, mDay);
            // DISPLAY THE DATE
            datePickerDialog.show();
        });

        /*// CREATE NOTIFICATION
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My Notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Get the NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Display the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());


        // NOTIFICATION FROM ORDER STATUS
        final int MILLISECONDS = 10000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                OrderAPI OrderAPI = APIClient.getClient().create(OrderAPI.class);
                Call<List<Order>> call = OrderAPI.getAllOrdersWithDate(today);

                call.enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        if(!response.isSuccessful()) {
                            // Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_LONG).show(); //DB-connection succeeded but couldnt process the request.
                            return;
                        }
                        List<Order> orders = response.body();
                        allOrdersWithDate.clear();
                        if(!Objects.requireNonNull(orders).isEmpty()) {
                            allOrdersWithDate.addAll(orders);
                        }
                        // WHAT TO DO WITH THE ORDERS FORM TODAY?
                        // CHECK STATUS ON EVERY ORDER

                        for (Order e : allOrdersWithDate) {
                            if(e.getStatus()){
                                //Toast.makeText(getApplicationContext(), "Bord " + e.getBooking().getTableNumber() +" har en beställning klar", Toast.LENGTH_LONG).show();

                                // DISPLAY NOTIFICATION

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        //Toast.makeText(getApplicationContext(), "DB-connection, failed" , Toast.LENGTH_LONG).show();
                    }
                });

                handler.postDelayed(this, MILLISECONDS);
            }
        }, MILLISECONDS);*/

        // BACK BUTTON
        Intent activityBack = new Intent(this, MainActivity.class);
        Employee employee = (Employee) getIntent().getSerializableExtra("Employee");
        activityBack.putExtra("Employee", employee);
        Button buttonBack = findViewById(R.id.button_back_tables);
        buttonBack.setOnClickListener(v -> startActivity(activityBack));
    }

    public void createListOfBookings(String date){
        BookingAPI bookingAPI = APIClient.getClient().create(BookingAPI.class);
        Call<List<Booking>> call = bookingAPI.getAllBookingWithDate(date);
        call.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(@NonNull Call<List<Booking>> call, @NonNull Response<List<Booking>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Helvete!" , Toast.LENGTH_LONG).show();
                    return;
                }
                List<Booking> booking = response.body();
                allBookingsWithDate.clear();
                if(!Objects.requireNonNull(booking).isEmpty()) {
                    allBookingsWithDate.addAll(booking);
                }
                createTablesFromBooking(allBookingsWithDate);
            }
            @Override
            public void onFailure(@NonNull Call<List<Booking>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network error, cannot reach DB." , Toast.LENGTH_LONG).show();
            }
        });
    }


    public void createTablesFromBooking(ArrayList<Booking> allBookings) {

        // PARAMETERS FOR THE Button
        final int WIDTH = 800;
        final int HEIGHT = 200;
        final int TEXT_SIZE = 16;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WIDTH, HEIGHT);
        params.setMargins(0, 5, 0, 5);
        Employee employee = (Employee) getIntent().getSerializableExtra("Employee");
        LinearLayout mainLayout = findViewById(R.id.button_layout);
        mainLayout.removeAllViews();

        /*
         * Creates TABLES_AMOUNT OF tables and add correct text to it. If a booking exists, then
         * add listener for activityOrder, else add listener for activityBooking.
         */
        for (int i : tableNumbers) {
            // VARIABLES
            String text = "BORD " + i + ".";
            // CREATES BUTTON
            Button button = new Button(this);
            button.setText(text);
            button.setTextSize(TEXT_SIZE);
            button.setGravity(Gravity.LEFT);
            button.setPadding(30, 30 , 0 ,0);
            button.setLayoutParams(params);
            button.setBackgroundResource(R.drawable.button_table);
            mainLayout.addView(button);
            Booking booking = getCurrentBooking(allBookings, i);

            if (booking != null) {
                button.setBackgroundResource(R.drawable.selected_table);
                String bookedText = text + "   " + booking.getTime() + " - " + booking.getFirstName();
                button.setText(bookedText);
                Intent activityOrder = new Intent(this, OrderActivity.class);
                activityOrder.putExtra("Booking", booking);
                activityOrder.putExtra("Employee", employee);
                button.setOnClickListener(v -> startActivity(activityOrder));
                button.setOnLongClickListener(v -> onItemHoldPress(booking));
            } else {
                Intent activityBooking = new Intent(this, BookingActivity.class);
                activityBooking.putExtra("CurrentTable", i);
                activityBooking.putExtra("Employee", employee);
                String s = editDate.getText().toString();
                activityBooking.putExtra("date", s);
                button.setOnClickListener(v -> startActivity(activityBooking));
            }
        }

    }

    public Booking getCurrentBooking(ArrayList<Booking> allBookings, int tableNumber){
        for(Booking booking : allBookings){
            if(booking.getTableNumber() == tableNumber){
                return booking;
            }
        }
        return null;
    }

    public boolean onItemHoldPress(Booking item) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parent = this.getWindow().findViewById(android.R.id.content);
        View popupView = inflater.inflate(R.layout.popup_booking_info, (ViewGroup) parent, false);
        TextView bookingInfo = popupView.findViewById(R.id.popup_display_info);
        TextView titleText = popupView.findViewById(R.id.note_title);
        Button buttonOK = popupView.findViewById(R.id.button_back);
        String title = "Info om bord " + item.getTableNumber() + ".";
        String info = "<b>Namn</b>:    " + item.getFirstName() + " " + item.getLastName() + "<br>"
                + "<b>Tid:</b>         " + item.getTime() + "<br>"
                + "<b>Antal</b>:       " + item.getNumberOfPeople() + "<br>"
                + "<b>Telefon:</b>     " + item.getPhoneNumber();

        // CREATE THE POPUP WINDOW
        final int WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
        final int HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, WIDTH, HEIGHT, true);

        // SHOW THE POPUP WINDOW
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        // SHOW TITLE
        titleText.setText(title);
        bookingInfo.setText(Html.fromHtml(info));

        // ON BUTTON PRESS, DISMISS VIEW
        buttonOK.setOnClickListener(v -> {popupWindow.dismiss();} );

        return true; // EVENT HAS BEEN HANDLED
    }

}