package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BookingActivity extends AppCompatActivity {
    private Button button_back;
    Table table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        button_back = findViewById(R.id.button_back_booking);
        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = (TextView) findViewById(R.id.booking_current_table);

        Booking new_book = new Booking("Alex Triceps", "Joel");
        table.setBooking(new_book);

        current_table.setText("Bord: "+table.getID());

        Intent activity_tables = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_tables));
    }
}