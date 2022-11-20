package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_servitor;
    private Button button_kok;

    /**
     * on startup
     * @param savedInstanceState - temp
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent activity_log_in = new Intent(this, LogInActivity.class);
        Intent activity_booking = new Intent(this, BookingActivity.class);

        button_servitor = findViewById(R.id.button_servitor);
        button_kok = findViewById(R.id.button_kok);

        button_servitor.setOnClickListener(new SwitchActivity(activity_log_in));
        button_kok.setOnClickListener(new SwitchActivity(activity_booking));
    }
}