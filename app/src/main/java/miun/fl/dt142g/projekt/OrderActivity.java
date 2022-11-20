package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    private Button button_back;
    private Spinner spinner_order;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        button_back = findViewById(R.id.button_back_order);

        /*
         * TEMP - incoming "data" from SQL table about Al Carté items
         * Perhaps use a for each loop from data from SQL after, below is only example
         */
        Order order_starter1 = new Order("Skagen Toast", "Förrätt", 30);
        Order order_starter2 = new Order("Halstrad tartar", "Förrätt", 35);
        Order order_starter3 = new Order("Husets soppa", "Förrätt", 20);
        Order order_main_course1 = new Order("Pizza", "Varmrätt", 70);
        Order order_dessert1 = new Order("Glass med kolasås", "Efterrätt", 45);
        Order order_drink1 = new Order("Cola", "Drink", 19);


        // INFO ABOUT TABLE
        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = (TextView) findViewById(R.id.order_current_table);
        current_table.setText("Bord: "+table.getID());
        // DROPDOWN
        spinner_order = findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_order.setAdapter(adapter);

        // BACK ACTIVITY
        Intent activity_booking = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_booking));
    }

}