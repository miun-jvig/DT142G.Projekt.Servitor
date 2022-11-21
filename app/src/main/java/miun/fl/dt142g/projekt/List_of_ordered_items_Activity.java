package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class List_of_ordered_items_Activity extends AppCompatActivity {
    private Button button_back;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ordered_items);

        Intent activity_order = new Intent(this, OrderActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_order));

    }
}