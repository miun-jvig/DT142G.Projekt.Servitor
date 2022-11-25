package miun.fl.dt142g.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SendOrderActivity extends AppCompatActivity {
    private Button button_back;
    private TextView text;
    private LinearLayout mLayout;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);


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
