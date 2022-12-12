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
import java.util.Calendar;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Carte;
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
        ArrayList<OrderContainer> orderList = (ArrayList<OrderContainer>) getIntent().getSerializableExtra("Order");
        list.setAdapter(new OrderListAdapter(this, orderList));

            Button button = findViewById(R.id.send_order_button);
            button.setOnClickListener(v -> {
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                String time = mHour + ":" + mMinute;
                for(OrderContainer e : orderList){
                    e.getOrder().setTime(time);
                    OrderAPI orderAPI = APIClient.getClient().create(OrderAPI.class);
                    Call<Order> call = orderAPI.postOrder(e.getOrder());
                    call.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_LONG).show(); //DB-connection succeeded but couldnt process the request.
                                return;
                            }
                        }
                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "DB-connection, failed" , Toast.LENGTH_LONG).show();
                        }
                    });
                }
                orderSucceded();
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
    public void orderSucceded(){
        Toast.makeText(getApplicationContext(), "Beställning skickad" , Toast.LENGTH_LONG).show();
        Intent activityTables = new Intent(this, TablesActivity.class);
        startActivity(activityTables);
    }
}
