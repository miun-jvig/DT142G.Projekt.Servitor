package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private Button button_back;
    private Spinner spinner_order;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        button_back = findViewById(R.id.button_back_order);


        // Temporary exampledishes until SQL is implemented
        Item tartar = new Item("Halstrad tartar", "Lättstekt tartar", "starter", 79.90, 1);
        Item curryChicken = new Item("Currykyckling", "Kyckling med curry", "main", 109.90, 2);
        Item carrotcake = new Item("Morotskaka", "Saftig morotskaka", "dessert", 54.90, 3);
        Item sprite = new Item("Sprite", "40cl Sprite", "beverage", 109.90, 4);

        ArrayList<Item> menuitems = new ArrayList<>();
        menuitems.add(tartar);
        menuitems.add(curryChicken);
        menuitems.add(carrotcake);
        menuitems.add(sprite);

        TableLayout layout = findViewById(R.id.TableLayout);
        for(int i = 0; i < layout.getChildCount(); i++){
            TableRow row = (TableRow) layout.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++){
                Button button = (Button)row.getChildAt(j);
                button.setBackgroundColor(getResources().getColor(R.color.appOrange));
            }
        }


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