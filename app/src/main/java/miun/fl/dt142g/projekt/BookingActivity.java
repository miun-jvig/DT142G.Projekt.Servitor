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
    private EditText editFirstName, editLastName, editTime, editPhone, editNumberOfPeople;
    private TextView error; //Displays the errormessage if the user is not inserting the right values.
    private int mHour;
    private int mMinute;
    private int numberOfPeople_int;
    private String numberOfPeople_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    //Variables
        TextView chosenDate;
        int currentTable = (int)getIntent().getSerializableExtra("CurrentTable");
        Button button_back = findViewById(R.id.button_back_booking);
        Button button_create_booking = findViewById(R.id.createBookingButton_id);
        TextView current_table = findViewById(R.id.booking_current_table);
        String text = "Bord: " + currentTable;

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        editFirstName = findViewById(R.id.first_name_id);
        editLastName = findViewById(R.id.last_name_id);
        editNumberOfPeople = findViewById(R.id.numberOfPeople_id);
        editTime = findViewById(R.id.time_id);
        chosenDate = findViewById(R.id.date_id);
        editPhone = findViewById(R.id.notes_id);
        error = findViewById(R.id.testText_id);

    //Default values
        error.setText(null);
        current_table.setText(text);

    //Get and set chosen Date
        String date = (String) getIntent().getSerializableExtra("date");
        chosenDate.setText(date);

    //Get and set Current Time and Date
        String todaysDate = changeDate(mYear, mMonth, mDay);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        String timeText = changeTime(mHour, mMinute);
        editTime.setText(timeText);

    //Time
        editTime.setOnClickListener(view -> {
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this,
                    (view1, hourOfDay, minute) -> {
                        String time = changeTime(hourOfDay, minute);
                        editTime.setText(time);
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });

    //inputs from form creates a booking
        button_create_booking.setOnClickListener(v -> {

            // Convert text to integer NUMBER OF PEOPLE

                numberOfPeople_str = editNumberOfPeople.getText().toString();
            if(!numberOfPeople_str.isEmpty()){
                numberOfPeople_int = Integer.parseInt(numberOfPeople_str);
            }
    /*
    *   Creating a booking does not require all fields if the guests are booking the same day. AKA a "Drop in".
    * */
        // NOT DROP-IN
            if (!todaysDate.equals(date) && (editFirstName.getText().toString().isEmpty() || editPhone.getText().toString().isEmpty())){
                // MISSING INFO FROM FORM
                String errorText = "Fyll i fälten!";
                error.setText(errorText);
            }
        //DROP-IN
            else {
                error.setText(null);
                Booking booking = new Booking();
                booking.setDate(date);
                booking.setTime(editTime.getText().toString());
                booking.setId(1);
                booking.setFirstName(editFirstName.getText().toString());
                booking.setLastName(editLastName.getText().toString());
                booking.setNumberOfPeople(numberOfPeople_int);
                booking.setTableNumber(currentTable);
                booking.setPhoneNumber(editPhone.getText().toString());

                BookingAPI BookingAPI = APIClient.getClient().create(BookingAPI.class);
                Call<Booking> call = BookingAPI.postBooking(booking);

                call.enqueue(new Callback<Booking>() {
                    @Override
                    public void onResponse(Call<Booking> call, Response<Booking> response) {
                            Toast.makeText(getApplicationContext(), "Bokning skapad" , Toast.LENGTH_LONG).show(); //DB-connection succeeded but couldnt process the request.
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

    //Back button
        Intent activity_tables = new Intent(this, TablesActivity.class);
        Employee employee = (Employee)getIntent().getSerializableExtra("Employee");
        activity_tables.putExtra("Employee", employee); //Also sending employee
        button_back.setOnClickListener(v -> startActivity(activity_tables));
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

    public String changeTime(int hour, int minute){
        String sHour = Integer.toString(hour);
        String sMinute = Integer.toString(minute);
        String full;

        if(hour < 10){
            sHour = "0" + hour;
        }
        if(minute < 10){
            sMinute = "0" + minute;
        }
        full = sHour + ":" + sMinute;
        return full;
    }
}
