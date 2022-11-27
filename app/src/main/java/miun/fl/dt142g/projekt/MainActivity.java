package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * on startup
     * @param savedInstanceState - temp
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent activity_tables = new Intent(this, TablesActivity.class);

        Button button_servitor = findViewById(R.id.button_servitor);
        Button button_kok = findViewById(R.id.button_kok);

        button_servitor.setOnClickListener(v -> startActivity(activity_tables));
    }
}