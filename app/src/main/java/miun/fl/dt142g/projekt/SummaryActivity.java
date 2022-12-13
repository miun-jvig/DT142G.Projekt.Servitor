package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.CombinedOrdersAPI;
import miun.fl.dt142g.projekt.json.Employee;
import miun.fl.dt142g.projekt.json.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {
    private Employee employee;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Button buttonBack = findViewById(R.id.button_back_summary);

        // LISTVIEW
        employee = (Employee) getIntent().getSerializableExtra("Employee");
        Booking booking = (Booking) getIntent().getSerializableExtra("Booking");

        CombinedOrdersAPI combinedOrdersAPI = APIClient.getClient().create(CombinedOrdersAPI.class);
        Call<List<List<Object>>> call = combinedOrdersAPI.getAllCombinedOrders(booking.getId());
        call.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, Response<List<List<Object>>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_LONG).show();
                    return;
                }
                List<List<Object>> resp = response.body();
                ArrayList<List<Object>> allOrders = (ArrayList<List<Object>>) resp; //Castar responsen till en ArrayList som ArrayAdapter kan använda
                createList(allOrders);
            }
            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Network error, cannot reach DB." , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
        // INFO ABOUT TABLE
        TextView currentTable = findViewById(R.id.summary_current_table);
        String currentTableNumber = "Bord: " + booking.getTableNumber();
        currentTable.setText(currentTableNumber);

        // BACK ACTIVITY
        Intent activityBooking = new Intent(this, OrderActivity.class);
        activityBooking.putExtra("Booking", booking);
        ArrayList<Order> orderList = (ArrayList<Order>) getIntent().getSerializableExtra("Order");
        activityBooking.putExtra("Order",orderList);
        activityBooking.putExtra("Employee", employee);
        buttonBack.setOnClickListener(v -> startActivity(activityBooking));
    }
    void createList(ArrayList<List<Object>> allOrders){
        ListView list = findViewById(R.id.listView_order);
        list.setAdapter(new SummaryAdapter(this, allOrders));
    }
}