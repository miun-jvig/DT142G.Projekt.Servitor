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

import miun.fl.dt142g.projekt.json.Carte;

public class SendOrderActivity extends AppCompatActivity {
    private Button button_back;
    private TextView text;
    private LinearLayout mLayout;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        // LISTVIEW
        ListView list = (ListView) findViewById(R.id.listView_order);
        ArrayList<Carte> order = (ArrayList<Carte>) getIntent().getSerializableExtra("Order");
        list.setAdapter(new OrderListAdapter(this, order));

        // INFO ABOUT TABLE
        Table table = (Table) getIntent().getSerializableExtra("Table");
        TextView currentTable = findViewById(R.id.booking_current_table);
        currentTable.setText("Bord: "+table.getID());

        // Back button
        Intent activityOrder = new Intent(this, OrderActivity.class);
        activityOrder.putExtra("Table", table);
        activityOrder.putExtra("Order", order);
        Button buttonBack = findViewById(R.id.button_back_listOfOrder);
        buttonBack.setOnClickListener(view -> startActivity(activityOrder));
    }
}
