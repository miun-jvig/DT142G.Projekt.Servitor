package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityServitor extends AppCompatActivity {
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevitor);
        Intent activity_main = new Intent(this, MainActivity.class);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new SwitchActivity(activity_main));
    }
}