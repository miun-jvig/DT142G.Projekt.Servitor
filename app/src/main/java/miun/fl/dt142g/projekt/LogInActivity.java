package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {
    private Button button_log_in;
    private TextView log_in_error_msg;
    private EditText user_input_pn; // USER INPUT PERSONAL NUMBER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // TEMP, IMAGINE PERSONAL NUMBER IS FROM SQL
        ArrayList<String> all_employees = new ArrayList<>();
        String temp_personal_joel = "199408175397";
        String temp_personal_alex = "";
        all_employees.add(temp_personal_alex);
        all_employees.add(temp_personal_joel);

        // -----
        button_log_in = findViewById(R.id.button_log_in);
        log_in_error_msg = findViewById(R.id.log_in_error);
        user_input_pn = findViewById(R.id.edit_personal_number);
        button_log_in.setOnClickListener(v -> log_in(all_employees));
    }

    public void log_in(ArrayList<String> all_employees){
        SharedPreferences pref = getSharedPreferences("Activity", Context.MODE_PRIVATE);
        String user_input = user_input_pn.getText().toString();
        String error_msg = "Personnumret \"" + user_input + "\" finns ej. Kontakta Anders!";
        Intent activity_main = new Intent(this, MainActivity.class);
        // CHECK IF PERSONAL NUMBER EXISTS IN DB
        for(String pn : all_employees){
            if(!user_input.equals(pn)){
                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("log_in_success", false);
                edt.apply();
            }
        }

        if(pref.getBoolean("log_in_success", true)){
            startActivity(activity_main);
        }
        else{
            log_in_error_msg.setText(error_msg);
        }
    }
}