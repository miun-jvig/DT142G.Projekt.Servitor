package miun.fl.dt142g.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class SendOrderActivity extends AppCompatActivity {
    private Button button_back;
    private TextView text;
    private LinearLayout mLayout;
    Table table;

    String[][] exampleData = {{"Data1", "Data2", "Data3"}, {"Data4","Data5", "Data6"}, {"Data4","Data5", "Data6"}, {"Data4","Data5", "Data6"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        // List of items in the order
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_order_list_view, exampleData);
        ListView list = (ListView) findViewById(R.id.listView_order);
        //list.setAdapter(adapter);
        list.setAdapter(new OrderListAdapter(this, exampleData));











        // INFO ABOUT TABLE
        table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = findViewById(R.id.booking_current_table);
        current_table.setText("Bord: "+table.getID());

        // Back button
        Intent activity_order = new Intent(this, OrderActivity.class);
        activity_order.putExtra("Table", table);
        button_back = findViewById(R.id.button_back_listOfOrder);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(activity_order);
            }
        });
    }
}
