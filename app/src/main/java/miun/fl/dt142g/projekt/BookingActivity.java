package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.BookingAPI;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    private EditText editName, editTime, editPhone, editNumberOfPeople;
    private TextView chosenDate;
    private TextView error;
    private int mHour;
    private int mMinute;
    private int numberOfPeople_int;
    private String numberOfPeople_str;
    private String todaysDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        int currentTable = (int)getIntent().getSerializableExtra("CurrentTable");
        if (currentTable == 0){
            Toast.makeText(getApplicationContext(), "0" , Toast.LENGTH_LONG).show();
        }
        TextView current_table = findViewById(R.id.booking_current_table);
        String text = "Bord: " + currentTable;
        current_table.setText(text);

        Button button_back = findViewById(R.id.button_back_booking);
        Button button_create_booking = findViewById(R.id.createBookingButton_id);
        editName = findViewById(R.id.name_id);
        editNumberOfPeople = findViewById(R.id.numberOfPeople_id);
        editTime = findViewById(R.id.time_id);
        chosenDate = findViewById(R.id.date_id);
        editPhone = findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

        // Default values
        error.setText(null);

        // Get and set chosen Date
        String date = (String) getIntent().getSerializableExtra("date");
        chosenDate.setText(date);

        // Get and set Current Time and Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        todaysDate = mYear + "-" + (mMonth + 1) + "-" + mDay;
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        String timeText = mHour + ":" + mMinute;
        editTime.setText(timeText);

        // TIME
        editTime.setOnClickListener(view -> {
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this,
                    (view1, hourOfDay, minute) -> {
                        String time = hourOfDay + ":" + minute;
                        editTime.setText(time);
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });

        // inputs from form creates a booking ------------------------------------------------
        button_create_booking.setOnClickListener(v -> {

            // Convert text to integer NUMBER OF PEOPLE
            numberOfPeople_str = editNumberOfPeople.getText().toString();
            numberOfPeople_int = Integer.parseInt(numberOfPeople_str);

            // NOT DROP-IN
            if (!todaysDate.equals(date) && (editName.getText().toString().isEmpty() || editPhone.getText().toString().isEmpty())){
                // MISSING INFO FROM FORM
                String errorText = "Fyll i fälten!";
                error.setText(errorText);
            }
            // DROP-IN
            else {
                error.setText(null);
                Booking booking = new Booking();
                booking.setDate(date);
                booking.setTime(timeText);
                booking.setId(1);
                booking.setFirstName(editName.getText().toString());
                booking.setLastName(editName.getText().toString());
                booking.setNumberOfPeople(numberOfPeople_int);
                booking.setTableNumber(currentTable);
                booking.setPhoneNumber(editPhone.getText().toString());

                BookingAPI BookingAPI = APIClient.getClient().create(BookingAPI.class);
                Call<Booking> call = BookingAPI.postBooking(booking);

                call.enqueue(new Callback<Booking>() {
                    @Override
                    public void onResponse(Call<Booking> call, Response<Booking> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_LONG).show(); //DB-connection succeeded but couldnt process the request.
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<Booking> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "DB-connection, failed" , Toast.LENGTH_LONG).show();
                    }
                });

                Intent activityTables = new Intent(this, TablesActivity.class);
                startActivity(activityTables);
            }
        });

        // Back button
        Intent activity_tables = new Intent(this, TablesActivity.class);
        Employee employee = (Employee)getIntent().getSerializableExtra("Employee");
        activity_tables.putExtra("Employee", employee);
        button_back.setOnClickListener(v -> startActivity(activity_tables));
    }

}
