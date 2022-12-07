package miun.fl.dt142g.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.CarteAPI;
import miun.fl.dt142g.projekt.json.Order;
import miun.fl.dt142g.projekt.json.OrderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        // LISTVIEW
        ListView list = findViewById(R.id.listView_order);
        ArrayList<Order> orderList = (ArrayList<Order>) getIntent().getSerializableExtra("Order");
        list.setAdapter(new OrderListAdapter(this, orderList));

        Button button = findViewById(R.id.send_order_button);
        button.setOnClickListener(v -> {
            OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
            Call<Order> call = orderAPI.postOrder(orderList.get(0));
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if(!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_LONG).show();
                    }
                    Order order = response.body();
                    System.out.println(order.getId());
                    System.out.println(order.getNote());
                    System.out.println(order.getBooking().getFirstName());
                    System.out.println(order.getDish().getName());
                }
                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "DB-connection, failed" , Toast.LENGTH_LONG).show();
                }
            });
        });

        // INFO ABOUT TABLE
        Booking booking = (Booking) getIntent().getSerializableExtra("Booking");
        TextView currentTable = findViewById(R.id.booking_current_table);
        String currentTableNumber = "Bord: " + booking.getTableNumber();
        currentTable.setText(currentTableNumber);

        // Back button
        Intent activityOrder = new Intent(this, OrderActivity.class);
        activityOrder.putExtra("Booking", booking);
        activityOrder.putExtra("Order", orderList);
        Button buttonBack = findViewById(R.id.button_back_listOfOrder);
        buttonBack.setOnClickListener(view -> startActivity(activityOrder));
    }
}
