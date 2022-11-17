package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LogInActivity extends AppCompatActivity {
    private Button button_log_in;
    private Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //CODE FOR CREDENTIALS - CHECK SQL, if OK, go to intent activity_tables
        Intent activity_tables = new Intent(this, TablesActivity.class);
        Intent activity_main = new Intent(this, MainActivity.class);
        button_log_in = findViewById(R.id.button_log_in);
        button_back = findViewById(R.id.button_back_2);
        button_log_in.setOnClickListener(new SwitchActivity(activity_tables));
        button_back.setOnClickListener(new SwitchActivity(activity_main));
    }
}