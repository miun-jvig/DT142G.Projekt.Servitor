package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class OrderActivity extends AppCompatActivity {
    private Button button_back;
    // GET FROM SQL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        button_back = findViewById(R.id.button_back_order);

        Intent activity_booking = new Intent(this, MainActivity.class);
        button_back.setOnClickListener(new SwitchActivity(activity_booking));

    }
}