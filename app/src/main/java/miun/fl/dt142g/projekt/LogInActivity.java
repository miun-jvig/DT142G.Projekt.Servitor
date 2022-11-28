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
    private Button buttonLogIn;
    private TextView logInErrorMsg;
    private EditText userInputPn; // USER INPUT PERSONAL NUMBER
    public static final String PREFS_NAME = "log_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // SharedPreferences stores value with key PREFS_NAME, works even though app closes
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean hasLoggedIn = settings.getBoolean(PREFS_NAME, false); // default value false
        Intent activityMain = new Intent(this, MainActivity.class);

        // If user has previously logged in, then continue
        if(hasLoggedIn){
            startActivity(activityMain);
        }

        // TEMP, IMAGINE PERSONAL NUMBER IS FROM SQL
        ArrayList<String> allEmployees = new ArrayList<>();
        String tempPersonalJoel = "199408175397";
        String tempPersonalAlex = "1337";
        allEmployees.add(tempPersonalAlex);
        allEmployees.add(tempPersonalJoel);

        // VIEWS
        buttonLogIn = findViewById(R.id.button_log_in);
        logInErrorMsg = findViewById(R.id.log_in_error);
        userInputPn = findViewById(R.id.edit_personal_number);
        buttonLogIn.setOnClickListener(v -> log_in(allEmployees, settings));
    }

    /**
     *  Function to log in only once using SharedPreferences.
     * @param allEmployees List of all personal numbers taken from a database
     * @param settings A SharedPreference containing a value, whether or not user has logged in previously
     */
    public void log_in(ArrayList<String> allEmployees, SharedPreferences settings){
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFS_NAME, false); // default value false
        editor.apply();
        String userInput = userInputPn.getText().toString();
        String errorMsg = "Personnumret \"" + userInput + "\" finns ej. Kontakta Anders!";
        Intent activityMain = new Intent(this, MainActivity.class);
        // CHECK IF PERSONAL NUMBER EXISTS IN DB
        for(String pn : allEmployees){
            if(userInput.equals(pn)){
                editor.putBoolean(PREFS_NAME, true);
                editor.apply();
                startActivity(activityMain);
                finish();
            }
        }

        // CHANGES TEXT DEPENDING IF PERSONAL NUMBER DOES NOT EXIST
        if(settings.getBoolean(PREFS_NAME, true)){
            logInErrorMsg.setText("");
        }
        else{
            logInErrorMsg.setText(errorMsg);
        }
    }
}