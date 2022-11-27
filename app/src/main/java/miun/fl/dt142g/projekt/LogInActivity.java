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
    public static final String PREFS_NAME = "log_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // SharedPreferences stores value with key PREFS_NAME, works even though app closes
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean has_logged_in = settings.getBoolean(PREFS_NAME, false); // default value false
        Intent activity_main = new Intent(this, MainActivity.class);

        // If user has previously logged in, then continue
        if(has_logged_in){
            startActivity(activity_main);
        }

        // TEMP, IMAGINE PERSONAL NUMBER IS FROM SQL
        ArrayList<String> all_employees = new ArrayList<>();
        String temp_personal_joel = "199408175397";
        String temp_personal_alex = "1337";
        all_employees.add(temp_personal_alex);
        all_employees.add(temp_personal_joel);

        // VIEWS
        button_log_in = findViewById(R.id.button_log_in);
        log_in_error_msg = findViewById(R.id.log_in_error);
        user_input_pn = findViewById(R.id.edit_personal_number);
        button_log_in.setOnClickListener(v -> log_in(all_employees, settings));
    }

    /**
     *  Function to log in only once using SharedPreferences.
     * @param all_employees List of all personal numbers taken from a database
     * @param settings A SharedPreference containing a value, whether or not user has logged in previously
     */
    public void log_in(ArrayList<String> all_employees, SharedPreferences settings){
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFS_NAME, false); // default value false
        editor.apply();
        String user_input = user_input_pn.getText().toString();
        String error_msg = "Personnumret \"" + user_input + "\" finns ej. Kontakta Anders!";
        Intent activity_main = new Intent(this, MainActivity.class);
        // CHECK IF PERSONAL NUMBER EXISTS IN DB
        for(String pn : all_employees){
            if(user_input.equals(pn)){
                editor.putBoolean(PREFS_NAME, true);
                editor.apply();
                startActivity(activity_main);
                finish();
            }
        }

        // CHANGES TEXT DEPENDING IF PERSONAL NUMBER DOES NOT EXIST
        if(settings.getBoolean(PREFS_NAME, true)){
            log_in_error_msg.setText("");
        }
        else{
            log_in_error_msg.setText(error_msg);
        }
    }
}