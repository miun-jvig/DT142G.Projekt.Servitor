package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Order;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Button buttonBack = findViewById(R.id.button_back_summary);

        ArrayList<Order> order = (ArrayList<Order>) getIntent().getSerializableExtra("Order");

        // INFO ABOUT TABLE
        Booking booking = (Booking) getIntent().getSerializableExtra("Booking");
        TextView currentTable = findViewById(R.id.summary_current_table);
        String currentTableNumber = "Bord: " + booking.getTableNumber();
        currentTable.setText(currentTableNumber);

        // BACK ACTIVITY
        Intent activityBooking = new Intent(this, OrderActivity.class);
        activityBooking.putExtra("Booking", booking);
        activityBooking.putExtra("Order", order);
        buttonBack.setOnClickListener(v -> startActivity(activityBooking));
    }
}