package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Employee;
import miun.fl.dt142g.projekt.json.EmployeeAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {
    private TextView logInErrorMsg;
    private EditText userInputPn; // USER INPUT PERSONAL NUMBER
    private static final String PREFS_NAME = "log_in";
    private static final String HAS_LOGGED_IN = "has_logged_in";
    private static final String EMPLOYEE_INFO = "employee_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // SharedPreferences stores value with key PREFS_NAME, works even though app closes
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean hasLoggedIn = settings.getBoolean(HAS_LOGGED_IN, false); // default value false
        Intent activityMain = new Intent(this, MainActivity.class);

        // If user has previously logged in, then continue
        if(hasLoggedIn){
            // CREATE GSON OBJECT TO RETRIEVE EMPLOYEE FROM SharedPreferences
            Gson gson = new Gson();
            String json = settings.getString(EMPLOYEE_INFO, "");
            Employee obj = gson.fromJson(json, Employee.class);
            activityMain.putExtra("Employee", obj);
           // startActivity(activityMain);
        }

        // VIEWS
        Button buttonLogIn = findViewById(R.id.button_log_in);
        logInErrorMsg = findViewById(R.id.log_in_error);
        userInputPn = findViewById(R.id.edit_personal_number);
        buttonLogIn.setOnClickListener(v -> log_in(settings));
    }

    /**
     *  Function to log in only once using SharedPreferences.
     */
    public void log_in(SharedPreferences settings){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(HAS_LOGGED_IN, false).apply(); // default value false
        String userInput = userInputPn.getText().toString();
        String errorMsg = "Personnumret \"" + userInput + "\" finns ej. Kontakta Anders!";
        Intent activityMain = new Intent(this, MainActivity.class);

        EmployeeAPI employeeAPI = APIClient.getClient().create(EmployeeAPI.class);
        Call<List<Employee>> call = employeeAPI.getEmployeeWithId(userInput);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(!response.isSuccessful()) {

                    return;
                }
                List<Employee> employee = response.body();
                if(!employee.isEmpty()) {
                    // CREATE A NEW GSON OBJECT TO PUT EMPLOYEE INTO SharedPreferences
                    Gson gson = new Gson();
                    String json = gson.toJson(employee.get(0));
                    editor.putBoolean(HAS_LOGGED_IN, true).apply();
                    editor.putString(EMPLOYEE_INFO, json).apply();
                    activityMain.putExtra("Employee", employee.get(0));
                    startActivity(activityMain);
                    finish();
                }
                else{
                    logInErrorMsg.setText(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network error." , Toast.LENGTH_LONG).show();
            }
        });
    }
}