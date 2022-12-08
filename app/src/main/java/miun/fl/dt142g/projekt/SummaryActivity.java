package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.BookingAPI;
import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.CarteAPI;
import miun.fl.dt142g.projekt.json.CombinedOrders;
import miun.fl.dt142g.projekt.json.Order;
import miun.fl.dt142g.projekt.json.OrderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {
    private final ArrayList<List<Object>> allOrders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Button buttonBack = findViewById(R.id.button_back_summary);

        // LISTVIEW
        ListView list = findViewById(R.id.listView_order);
        Booking booking = (Booking) getIntent().getSerializableExtra("Booking");

        OrderAPI OrderAPI = APIClient.getClient().create(OrderAPI.class);
        Call<List<List<Object>>> call = OrderAPI.getAllCombinedOrders(415);
        call.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, Response<List<List<Object>>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_LONG).show();
                    return;
                }
                List<List<Object>> orders = response.body();
                allOrders.addAll(orders);
                Toast.makeText(getApplicationContext(), Integer.toString(orders.size()), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Network error, cannot reach DB." , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });

        //list.setAdapter(new SummaryAdapter(this, allOrders));

        // INFO ABOUT TABLE
        TextView currentTable = findViewById(R.id.summary_current_table);
        String currentTableNumber = "Bord: " + booking.getTableNumber();
        currentTable.setText(currentTableNumber);

        // BACK ACTIVITY
        Intent activityBooking = new Intent(this, OrderActivity.class);
        activityBooking.putExtra("Booking", booking);
        ArrayList<Order> orderList = (ArrayList<Order>) getIntent().getSerializableExtra("Order");
        activityBooking.putExtra("Order",orderList);
        buttonBack.setOnClickListener(v -> startActivity(activityBooking));
    }
}