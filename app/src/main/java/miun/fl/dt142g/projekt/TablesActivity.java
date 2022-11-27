package miun.fl.dt142g.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TablesActivity extends AppCompatActivity {
    private final Table table1 = new Table(1);
    private final Table table2 = new Table(2);
    private final Table table3 = new Table(3);
    private final Table table4 = new Table(4);
    private final Table table5 = new Table(5);
    private final Table table6 = new Table(6);
    private final Table table7 = new Table(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        /*
         * TEMP - incoming "data" from SQL table. Containing zero orders, add using new_book.addOrderItem(order) will be used in OrderActivity
         * Perhaps use a for each loop from data from SQL after, below is only example
         */
        Booking new_book = new Booking("Nikki sur", "Alex", "note"); // "acquired" from SQL
        Booking new_book2 = new Booking("Alex Bicep", "Joel", "note"); // "acquired" from SQL
        table1.setBooking(new_book);
        table3.setBooking(new_book2);
        table1.setStatus(true);
        table3.setStatus(true);

        ArrayList<Table> all_tables = new ArrayList<>(); // all bookings for the date to be added to this
        all_tables.add(table1); // acting as added from SQL
        all_tables.add(table3); // acting as added from SQL

        for(Table table : all_tables){
            Button tmp;
            int button_identifier = getResources().getIdentifier("button_table" + table.getID(), "id", getPackageName());
            tmp = findViewById(button_identifier);
            tmp.setBackgroundResource(R.drawable.selected_table);
        }

        Intent activity_back = new Intent(this, MainActivity.class);
        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(v -> startActivity(activity_back));
    }

    public void onClick(@NonNull View view){
        switch(view.getId()) {
            case R.id.button_table1:
                switchActivityIfBooked(table1);
            break;
            case R.id.button_table2:
                switchActivityIfBooked(table2);
            break;
            case R.id.button_table3:
                switchActivityIfBooked(table3);
            break;
            case R.id.button_table4:
                switchActivityIfBooked(table4);
            break;
            case R.id.button_table5:
                switchActivityIfBooked(table5);
            break;
            case R.id.button_table6:
                switchActivityIfBooked(table6);
            break;
            case R.id.button_table7:
                switchActivityIfBooked(table7);
            break;
        }
    }

    public void switchActivityIfBooked(Table table){
        Intent activity_booking = new Intent(this, BookingActivity.class);
        Intent activity_order = new Intent(this, OrderActivity.class);
        activity_order.putExtra("Table", table);
        activity_booking.putExtra("Table", table);

        View view = this.getWindow().findViewById(android.R.id.content);

        if(table.getStatus()){
            view.getContext().startActivity(activity_order);
        }
        else{
            view.getContext().startActivity(activity_booking);
        }
    }
}