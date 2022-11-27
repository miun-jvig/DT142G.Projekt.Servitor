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
    private TextView text;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        // LISTVIEW
        ListView list = (ListView) findViewById(R.id.listView_order);
        ArrayList<Item> order = (ArrayList<Item>) getIntent().getSerializableExtra("Order");
        list.setAdapter(new OrderListAdapter(this, order));

        // INFO ABOUT TABLE
        Table table = (Table) getIntent().getSerializableExtra("Table");
        TextView current_table = findViewById(R.id.booking_current_table);
        current_table.setText("Bord: "+table.getID());

        // Back button
        Intent activity_order = new Intent(this, OrderActivity.class);
        activity_order.putExtra("Table", table);
        activity_order.putExtra("Order", order);
        Button button_back = findViewById(R.id.button_back_listOfOrder);
        button_back.setOnClickListener(view -> startActivity(activity_order));
    }
}
