package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class OrderActivity extends AppCompatActivity {
    private Button button_back;
    private Spinner spinner_order;
    // GET FROM SQL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        button_back = findViewById(R.id.button_back_order);
        spinner_order = findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ratter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_order.setAdapter(adapter);

        Intent activity_booking = new Intent(this, MainActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_booking));

    }
}