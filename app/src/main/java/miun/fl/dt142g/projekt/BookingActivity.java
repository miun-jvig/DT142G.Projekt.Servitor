package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editAmount;
    private EditText editTime;
    private EditText editNote;
    private TextView error;
    Table table;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = (TextView) findViewById(R.id.booking_current_table);
        current_table.setText("Bord "+table.getID());

        Button button_back = (Button) findViewById(R.id.button_back_booking);
        Button button_create_booking = (Button) findViewById(R.id.createBookingButton_id);
        editName = (EditText)findViewById(R.id.name_id);
        editAmount = (EditText)findViewById(R.id.amount_id);
        editTime = (EditText)findViewById(R.id.arrivalTime_id);
        editNote = (EditText)findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

        // Default values
        error.setText(null);
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR);
        int mMinute = calendar.get(Calendar.MINUTE);
        editTime.setText("Idag kl. "+ mHour +":"+ mMinute);

        // calendar popup picker
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookingActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // inputs from form creates a booking
        button_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentTime = mHour +":"+ mMinute;
                if (editTime.getText().toString().compareTo(currentTime) != 0 ){
                    if (editName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty()){
                        error.setText("Fyll i namn och antal fält");
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
        button_back.setOnClickListener(new SwitchActivity(activity_tables));
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        editTime.setText(dateFormat.format(myCalendar.getTime()));
    }

}