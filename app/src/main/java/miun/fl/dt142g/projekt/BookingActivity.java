package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import miun.fl.dt142g.projekt.json.Employee;

public class BookingActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editAmount;
    private EditText editTime;
    private EditText editNote;
    private TextView chosenDate;
    private TextView error;
    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        int currentTable = (int) getIntent().getSerializableExtra("CurrentTable");
        TextView current_table = findViewById(R.id.booking_current_table);
        String date = (String) getIntent().getSerializableExtra("date");
        String text = currentTable + date;
        current_table.setText(text);

        Button button_back = findViewById(R.id.button_back_booking);
        Button button_create_booking = findViewById(R.id.createBookingButton_id);
        editName = findViewById(R.id.name_id);
        editAmount = findViewById(R.id.amount_id);
        editTime = findViewById(R.id.time_id);
        chosenDate = findViewById(R.id.date_id);
        editNote = findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

        // Default values
        error.setText(null);

        // Get and set chosen Date
        //String date = (String) getIntent().getSerializableExtra("date");
        chosenDate.setText(date);

        // Get and set Current Time
        final Calendar c = Calendar.getInstance();
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
            String errorText = "Fyll i namn och antal:";
            if (editName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty()){
                error.setText(errorText);
            }
            else if (editName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty() || editTime.getText().toString().isEmpty() || editNote.getText().toString().isEmpty()) {// if the user missed filling a block
                //Booking new_book = new Booking("NULL", "NULL", currentTime, "NULL");
                //table.setStatus(true);
            }
            else {// if the user fills the details correctly
                error.setText(null);
                //Booking new_book = new Booking(editName.getText().toString(), editAmount.getText().toString(), editTime.getText().toString(), editNote.getText().toString());
                //table.setBooking(new_book);
            }
        });

        // Back button
        Intent activity_tables = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(v -> startActivity(activity_tables));
    }
}
