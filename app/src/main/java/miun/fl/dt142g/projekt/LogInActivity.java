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

import java.util.ArrayList;
import java.util.List;

import miun.fl.dt142g.projekt.json.Employee;
import miun.fl.dt142g.projekt.json.EmployeeAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
    private Button buttonLogIn;
    private TextView logInErrorMsg;
    private EditText userInputPn; // USER INPUT PERSONAL NUMBER
    public static final String PREFS_NAME = "log_in";
    // public final Employee employee = new Employee();

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

        // VIEWS
        buttonLogIn = findViewById(R.id.button_log_in);
        logInErrorMsg = findViewById(R.id.log_in_error);
        userInputPn = findViewById(R.id.edit_personal_number);
        buttonLogIn.setOnClickListener(v -> log_in(settings));
    }

    /**
     *  Function to log in only once using SharedPreferences.
     * @param settings A SharedPreference containing a value, whether or not user has logged in previously
     */
    public void log_in(SharedPreferences settings){
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFS_NAME, false); // default value false
        editor.apply();
        String userInput = userInputPn.getText().toString();
        String errorMsg = "Personnumret \"" + userInput + "\" finns ej. Kontakta Anders!";
        Intent activityMain = new Intent(this, MainActivity.class);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();


        String samuel = "10.82.231.15";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + samuel + ":8080/antons-skafferi-db-1.0-SNAPSHOT/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> call = employeeAPI.getEmployeeWithId(userInput);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(!response.isSuccessful()) {
                    // dåligt svar
                    return;
                }
                List<Employee> employee = response.body();
                if(!employee.isEmpty()) {
                    editor.putBoolean(PREFS_NAME, true);
                    editor.apply();
                    startActivity(activityMain);
                    finish();
                }else{
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