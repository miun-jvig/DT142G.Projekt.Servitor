package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Employee;

public class TablesActivity extends AppCompatActivity {
    private EditText editDate;
    private int mYear, mMonth, mDay;
    private final Booking booking = new Booking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        Intent activityBooking = new Intent(this, BookingActivity.class);
        Intent activityOrder = new Intent(this, OrderActivity.class);
        Employee employee = (Employee) getIntent().getSerializableExtra("Employee");
        activityOrder.putExtra("Booking", booking);
        activityBooking.putExtra("Booking", booking);
        activityOrder.putExtra("Employee", employee);
        final int TABLES_AMOUNT = 7;

        //--
        booking.setDate("2022-11-29");
        booking.setId(1);
        booking.setTableNumber(5);
        booking.setNumberOfPeople(3);
        booking.setTime("11:30:00");
        booking.setFirstName("Joel");
        booking.setLastName("Viggesjoo");
        booking.setPhoneNumber("0703980483");
        //--

        ArrayList<Booking> allBookings = new ArrayList<>(); // all bookings for the date to be added to this
        allBookings.add(booking); // acting as added from SQL

        // PARAMETERS FOR THE Button
        final int WIDTH = 800;
        final int HEIGHT = 200;
        final int TEXT_SIZE = 12;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WIDTH, HEIGHT);
        params.setMargins(0, 5, 0, 5);
        LinearLayout mainLayout = findViewById(R.id.button_layout);

        for(int i = 1; i <= TABLES_AMOUNT; i++){
            // VARIABLES
            String text = "BORD " + i;
            int backGroundResource = R.drawable.button_table;
            // CREATES BUTTON
            Button button = new Button(this);
            button.setText(text);
            button.setTextSize(TEXT_SIZE);
            button.setLayoutParams(params);
            button.setBackgroundResource(backGroundResource);
            mainLayout.addView(button);

            for(Booking booking : allBookings){
                if(booking.getTableNumber() == i){
                    button.setBackgroundResource(R.drawable.selected_table);
                    button.setOnClickListener(v -> startActivity(activityOrder));
                }
                else{
                    button.setOnClickListener(v -> startActivity(activityBooking));
                }
            }
        }

        // DATE VIEW
        // variables
        editDate = (EditText)findViewById(R.id.date_choice); // the chosen date
        // Get and set Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        String dateText = mDay + " - " + (mMonth + 1);
        editDate.setText(dateText);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(TablesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + " - " + (monthOfYear + 1);
                                editDate.setText(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        Intent activityBack = new Intent(this, MainActivity.class);
        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> startActivity(activityBack));
    }
}