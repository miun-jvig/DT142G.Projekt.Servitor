package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
        Item sprite = new Item("Sprite", "40cl Sprite", "beverage", 29.90, 4);
        Item monster = new Item("Monster", "50cl Monster", "beverage", 5.90, 5);

        Stack<Item> menuitems = new Stack<>();
        menuitems.add(tartar);
        menuitems.add(curryChicken);
        menuitems.add(carrotcake);
        menuitems.add(sprite);
        menuitems.add(monster);

        /*
        * Adds all items and styles them accordingly
        * */
        TableLayout layout = findViewById(R.id.TableLayout);
        int added_items = 0;
        while(!menuitems.empty() && added_items < 15){
            Item item = menuitems.pop(); //Item to be added to the view

            TableRow row = (TableRow)layout.getChildAt((added_items)/3); //The row for the item to be added to
            Button button = (Button)row.getChildAt((added_items)%3); //The cell the item will be added to

        // Makes the button visible and styles it accordingly
            button.setVisibility(View.VISIBLE);
            button.setBackgroundColor(getResources().getColor(getColorFromCategory(item.getCategory())));
            button.setText(item.getName()+"\n"+item.getPrice()+":-");
            added_items++;
        }


        // INFO ABOUT TABLE
        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = findViewById(R.id.order_current_table);
        current_table.setText("Bord: "+table.getID());

        // BACK ACTIVITY
        Intent activity_booking = new Intent(this, TablesActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_booking));

        // DROPDOWN
        spinner_order = (Spinner)findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_order.setAdapter(adapter);
        spinner_order.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = (String)parent.getItemAtPosition(pos);
        Toast.makeText(getApplicationContext(), selected , Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"OnNothingSelected" , Toast.LENGTH_LONG).show();
    }


    public int getColorFromCategory(String cat){
        switch(cat){
            case "starter":
                return R.color.foodYellow;
            case "main":
                return R.color.foodBlue;
            case "dessert":
                return R.color.foodPurple;
            case "beverage":
                return R.color.foodGreen;
        }
        return R.color.black;
    }
}