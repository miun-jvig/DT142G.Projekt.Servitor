package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import miun.fl.dt142g.projekt.json.Employee;

public class MainActivity extends AppCompatActivity {

    /**
     * on startup
     * @param savedInstanceState - temp
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Employee employee = (Employee) getIntent().getSerializableExtra("Employee");
        Intent activityTables = new Intent(this, TablesActivity.class);
        activityTables.putExtra("Employee", employee);

        Button buttonServitor = findViewById(R.id.button_servitor);
        Button buttonKok = findViewById(R.id.button_kok);
        buttonServitor.setOnClickListener(v -> startActivity(activityTables));
    }
}