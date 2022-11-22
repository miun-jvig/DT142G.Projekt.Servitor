package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editAmount;
    private EditText editTime;
    private EditText editNote;
    private TextView error;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = (TextView) findViewById(R.id.booking_current_table);
        current_table.setText("@string/table"+table.getID());

        Button button_back = (Button) findViewById(R.id.button_back_booking);
        Button button_create_booking = (Button) findViewById(R.id.createBookingButton_id);
        editName = (EditText)findViewById(R.id.name_id);
        editAmount = (EditText)findViewById(R.id.amount_id);
        editTime = (EditText)findViewById(R.id.arrivalTime_id);
        editNote = (EditText)findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

        // Default values
        error.setText("test");
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR);
        int mMinute = calendar.get(Calendar.MINUTE);
        editTime.setText(mHour +":"+ mMinute);

        // inputs from form creates a booking
        button_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentTime = mHour +":"+ mMinute;
                if (editTime.getText().toString().compareTo(currentTime) != 0 ){
                    if (editName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty()){
                        error.setText("@string/errorMessage");
                    }
                }
                else if (editName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty() || editTime.getText().toString().isEmpty() || editNote.getText().toString().isEmpty()) {// if the user missed filling a block
                    Booking new_book = new Booking("NULL", "NULL", currentTime, "NULL");
                    table.setStatus(true);
                }
                else {// if the user fills the details correctly
                    error.setText(null);
                    Booking new_book = new Booking(editName.getText().toString(), editAmount.getText().toString(), editTime.getText().toString(), editNote.getText().toString());
                    table.setBooking(new_book);
                }
            }
        });

        // Back button
        Intent activity_tables = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_tables));
    }
}