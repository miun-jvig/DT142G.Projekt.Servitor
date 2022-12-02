package miun.fl.dt142g.projekt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.BookingAPI;
import miun.fl.dt142g.projekt.json.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablesActivity extends AppCompatActivity {
    private final ArrayList<Booking> allBookings = new ArrayList<>(); // all bookings for the date to be added to this
    private EditText editDate;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        BookingAPI bookingAPI = APIClient.getClient().create(BookingAPI.class);
        Call<List<Booking>> call = bookingAPI.getAllBooking();
        call.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Helvete!" , Toast.LENGTH_LONG).show();
                    return;
                }
                List<Booking> booking = response.body();
                if(!Objects.requireNonNull(booking).isEmpty()) {
                    allBookings.addAll(booking);
                    createTablesFromBooking(allBookings);
                }
            }
            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network error, cannot reach DB." , Toast.LENGTH_LONG).show();
            }
        });
        Intent activityBack = new Intent(this, MainActivity.class);
        Button buttonBack = findViewById(R.id.button_back_tables);
        buttonBack.setOnClickListener(v -> startActivity(activityBack));
    }

    public void createTablesFromBooking(ArrayList<Booking> allBookings) {
        // AMOUNT OF TABLES
        final int TABLES_AMOUNT = 7;
        // PARAMETERS FOR THE Button
        final int WIDTH = 800;
        final int HEIGHT = 200;
        final int TEXT_SIZE = 12;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WIDTH, HEIGHT);
        params.setMargins(0, 5, 0, 5);
        Employee employee = (Employee) getIntent().getSerializableExtra("Employee");
        LinearLayout mainLayout = findViewById(R.id.button_layout);

        /*
         * Creates TABLES_AMOUNT OF tables and add correct text to it. If a booking exists, then
         * add listener for activityOrder, else add listener for activityBooking.
         */
        for (int i = 1; i <= TABLES_AMOUNT; i++) {
            // VARIABLES
            String text = "BORD " + i;
            // CREATES BUTTON
            Button button = new Button(this);
            button.setText(text);
            button.setTextSize(TEXT_SIZE);
            button.setLayoutParams(params);
            button.setBackgroundResource(R.drawable.button_table);
            mainLayout.addView(button);
            Booking booking = getCurrentBooking(allBookings, i);

            if (booking != null) {
                button.setBackgroundResource(R.drawable.selected_table);
                Intent activityOrder = new Intent(this, OrderActivity.class);
                activityOrder.putExtra("Booking", booking);
                activityOrder.putExtra("Employee", employee);
                button.setOnClickListener(v -> startActivity(activityOrder));
            } else {
                Intent activityBooking = new Intent(this, BookingActivity.class);
                activityBooking.putExtra("CurrentTable", i);
                activityBooking.putExtra("Employee", employee);
                button.setOnClickListener(v -> startActivity(activityBooking));
            }
        }

        // DATE VIEW
        // variables
        editDate = findViewById(R.id.date_choice); // the chosen date
        // Get and set Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String dateText = mYear + "-" + (mMonth + 1) + "-" + mDay;
        editDate.setText(dateText);

        editDate.setOnClickListener(view -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(TablesActivity.this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String date = year + "-"  + (monthOfYear + 1) + "-" + dayOfMonth;
                        editDate.setText(date);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }
    public Booking getCurrentBooking(ArrayList<Booking> allBookings, int tableNumber){
        for(Booking booking : allBookings){
            if(booking.getTableNumber() == tableNumber){
                return booking;
            }
        }
        return null;
    }

}