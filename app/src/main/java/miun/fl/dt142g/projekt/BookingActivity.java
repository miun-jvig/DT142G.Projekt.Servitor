package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import miun.fl.dt142g.projekt.json.Booking;

public class BookingActivity extends AppCompatActivity {
    private EditText editName, editAmount, editTime, editDate, editNote;
    private TextView error;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        booking = (Booking) getIntent().getSerializableExtra("Booking");
        TextView current_table = (TextView) findViewById(R.id.booking_current_table);
        current_table.setText("Bord: "+ booking.getTableNumber());

        Button button_back = (Button) findViewById(R.id.button_back_booking);
        Button button_create_booking = (Button) findViewById(R.id.createBookingButton_id);
        editName = (EditText)findViewById(R.id.name_id);
        editAmount = (EditText)findViewById(R.id.amount_id);
        editTime = (EditText)findViewById(R.id.time_id);
        editDate = (EditText)findViewById(R.id.date_id);
        editNote = (EditText)findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

        // Default values
        error.setText(null);

        // Get and set Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        editDate.setText(mDay + " - " + (mMonth + 1) + " - " + mYear);

        // Get and set Current Time
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        editTime.setText(mHour + ":" + mMinute);

        editDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editDate.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        // inputs from form creates a booking ------------------------------------------------
        button_create_booking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentDay = mDay + " - " + mMonth + " - " + mYear;
                String text = "Fyll i namn och antal:";
                if (editDate.getText().toString().compareTo(currentDay) != 0 ){
                    if (editName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty()){
                        error.setText(text);
                    }
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
            }
        });

        // Back button
        Intent activity_tables = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(v -> startActivity(activity_tables));
    }
}
