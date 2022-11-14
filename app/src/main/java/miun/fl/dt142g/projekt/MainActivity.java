package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_servitor;
    private Button button_kok;

    private void NextActivity() {
        Intent activity_servitor = new Intent(this, ActivityServitor.class);
        startActivity(activity_servitor);
    }
    public class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view){ NextActivity(); }
    }

    /**
     * on startup
     * @param savedInstanceState - temp
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_servitor = findViewById(R.id.button_servitor);
        button_kok = findViewById(R.id.button_kok);
        button_servitor.setOnClickListener(new ButtonListener());
    }
}