package miun.fl.dt142g.projekt;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.BookingAPI;
import miun.fl.dt142g.projekt.json.Employee;
import miun.fl.dt142g.projekt.json.Order;
import miun.fl.dt142g.projekt.json.OrderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablesActivity extends AppCompatActivity {
    private final ArrayList<Booking> allBookingsWithDate = new ArrayList<>(); // all bookings for the date to be added to this
    private final ArrayList<Order> allOrdersReady = new ArrayList<>();
    private TextView editDate;
    private String dateText;
    private int mYear, mMonth, mDay;
    private final int DELAY_MS = 10000;
    private final Handler HANDLER = new Handler();
    private final OrderAPI ORDER_API = APIClient.getClient().create(OrderAPI.class);
    private final BookingAPI BOOKING_API = APIClient.getClient().create(BookingAPI.class);
    private final String NON_SUCCESSFUL_RESPONSE = "Something went wrong.";
    private final String FAILED_DB_CONNECTION = "Network error, cannot reach database.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        // DATE VIEW________________________
        editDate = findViewById(R.id.date_choice); // the chosen date
        // Get and set Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateText = changeDate(mYear, mMonth, mDay);
        editDate.setText(dateText);

        createListOfBookings(dateText);

        // Change the date
        editDate.setOnClickListener(view -> {
            // PICK A DATE
            DatePickerDialog datePickerDialog = new DatePickerDialog(TablesActivity.this,
                    (datePicker, year, month, day) -> {
                        String dateTText = changeDate(year, month, day);
                        editDate.setText(dateTText);
                        dateText = dateTText;
                        createListOfBookings(dateTText);
                    }, mYear, mMonth, mDay);
            // DISPLAY THE DATE
            datePickerDialog.show();
        });

        // NOTIFICATION FROM ORDER STATUS
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<List<Order>> call = ORDER_API.getAllOrdersReady();

                call.enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), NON_SUCCESSFUL_RESPONSE, Toast.LENGTH_LONG).show(); //DB-connection succeeded but couldn't process the request.
                            return;
                        }
                        List<Order> orders = response.body();
                        allOrdersReady.clear();
                        if(!Objects.requireNonNull(orders).isEmpty()) {
                            allOrdersReady.addAll(orders);
                        }

                        for (Order e : allOrdersReady) {
                            createNotificationChannel();
                            sendNotification(e.getBooking().getTableNumber());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), FAILED_DB_CONNECTION, Toast.LENGTH_LONG).show();
                    }
                });

                HANDLER.postDelayed(this, DELAY_MS);
            }
        }, DELAY_MS);
    }

    public String changeDate(int year, int month, int date){
        month = month + 1;
        String sMonth = Integer.toString(month);
        String sDate = Integer.toString(date);
        String full;

        if(month < 10){
            sMonth = "0" + month;
        }
        if(date < 10){
            sDate = "0" + date;
        }
        full = year + "-" + sMonth + "-" + sDate;
        return full;
    }

    public void createListOfBookings(String date){
        Call<List<Booking>> call = BOOKING_API.getAllBookingWithDate(date);
        call.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(@NonNull Call<List<Booking>> call, @NonNull Response<List<Booking>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), NON_SUCCESSFUL_RESPONSE, Toast.LENGTH_LONG).show();
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
            public void onFailure(@NonNull Call<List<Booking>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), FAILED_DB_CONNECTION, Toast.LENGTH_LONG).show();
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
        // fill the vector tableNumbers
        int NUMBER_OF_TABLES = 6;
        for (int i = 1; i <= NUMBER_OF_TABLES; i++) {
            // VARIABLES
            String text = "BORD " + i + ".";
            // CREATES BUTTON
            Button button = new Button(this);
            button.setText(text);
            button.setTextSize(TEXT_SIZE);
            button.setGravity(Gravity.START);
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
        buttonOK.setOnClickListener(v -> popupWindow.dismiss());

        return true; // EVENT HAS BEEN HANDLED
    }

    private static final String CHANNEL_ID = "new_channel";
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(int tableNumber) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Beställning redo att serveras!")
                .setContentText("Bord: " + tableNumber)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(tableNumber, builder.build());
    }
}