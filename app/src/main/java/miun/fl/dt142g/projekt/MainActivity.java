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

        Intent activityTables = new Intent(this, TablesActivity.class);

        Button buttonServitor = findViewById(R.id.button_servitor);
        Button buttonKok = findViewById(R.id.button_kok);

        buttonServitor.setOnClickListener(v -> startActivity(activityTables));
    }
}