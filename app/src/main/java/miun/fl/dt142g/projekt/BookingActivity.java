package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import miun.fl.dt142g.projekt.json.Booking;

public class BookingActivity extends AppCompatActivity {
    private EditText editName, editTime, editPhone;
    private TextView chosenDate;
    private TextView error;
    private int mHour;
    private int mMinute;
    private int editAmount;
    private String dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        int currentTable = (int) getIntent().getSerializableExtra("CurrentTable");
        TextView current_table = findViewById(R.id.booking_current_table);
        String text = "Bord: " + currentTable;
        current_table.setText(text);

        Button button_back = findViewById(R.id.button_back_booking);
        Button button_create_booking = findViewById(R.id.createBookingButton_id);
        editName = findViewById(R.id.name_id);
        //editAmount = findViewById(R.id.amount_id);
        editTime = findViewById(R.id.time_id);
        chosenDate = findViewById(R.id.date_id);
        editPhone = findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

        editAmount = 2;

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
        dateText = mYear + "-" + (mMonth + 1) + "-" + mDay;
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
            String errorText = "Fyll i fälten!";
            if (!Objects.equals(dateText, chosenDate.getText().toString())){
                if (editName.getText().toString().isEmpty()){
                    error.setText(errorText);
                }
            }
            else {// if the user fills the details correctly
                error.setText(null);
                Booking booking = new Booking();
                booking.setDate(dateText);
                booking.setTime(timeText);
                booking.setFirstName(String.valueOf(editName));
                booking.setLastName(String.valueOf(editName));
                booking.setNumberOfPeople(editAmount);
                booking.setTableNumber(currentTable);
                booking.setPhoneNumber(String.valueOf(editPhone));
                String txt = "Bokar bord " + currentTable;
                Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
                Intent activityBooking = new Intent(this, BookingActivity.class);
                startActivity(activityBooking);
            }
        });

        // Back button
        Intent activity_tables = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(v -> startActivity(activity_tables));
    }

}
