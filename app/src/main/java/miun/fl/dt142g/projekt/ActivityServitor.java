package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityServitor extends AppCompatActivity {
    private Button back_button;

    private void NextActivity() {
        Intent main_activity = new Intent(this, MainActivity.class);
        startActivity(main_activity);
    }
    public class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view){ NextActivity(); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevitor);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new ButtonListener());
    }
}