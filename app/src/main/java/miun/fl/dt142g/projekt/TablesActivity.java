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
    //private final Booking booking = new Booking(); hämtad från databas

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

        ArrayList<Table> allTables = new ArrayList<>(); // all bookings for the date to be added to this
        allTables.add(table1); // acting as added from SQL
        allTables.add(table3); // acting as added from SQL

        for(Table table : allTables){
            Button tmp;
            int buttonIdentifier = getResources().getIdentifier("button_table" + table.getID(), "id", getPackageName());
            tmp = findViewById(buttonIdentifier);
            tmp.setBackgroundResource(R.drawable.selected_table);
        }

        Intent activityBack = new Intent(this, MainActivity.class);
        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> startActivity(activityBack));
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
        Intent activityBooking = new Intent(this, BookingActivity.class);
        Intent activityOrder = new Intent(this, OrderActivity.class);
        activityOrder.putExtra("Table", table);
        activityBooking.putExtra("Table", table);

        View view = this.getWindow().findViewById(android.R.id.content);

        if(table.getStatus()){
            view.getContext().startActivity(activityOrder);
        }
        else{
            view.getContext().startActivity(activityBooking);
        }
    }
}