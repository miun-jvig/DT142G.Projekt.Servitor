package miun.fl.dt142g.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.CarteAPI;
import miun.fl.dt142g.projekt.json.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListAdapter extends ArrayAdapter<Order>{

    private final Context context;
    private final ArrayList<Order> orderList;

    public OrderListAdapter(Context context, ArrayList<Order> order) {
        super(context, R.layout.activity_order_list_view, order);
        this.context = context;
        this.orderList = order;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_order_list_view, parent, false);
        TextView textView1 = rowView.findViewById(R.id.dish_name);
        TextView textView2 = rowView.findViewById(R.id.dish_price);
        TextView textView3 = rowView.findViewById(R.id.dish_note);
        Button deleteButton = rowView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            orderList.remove(position);
            notifyDataSetChanged();
        });
        textView1.setText(orderList.get(position).getDish().getName());
        //String price = getPrice(orderList.get(0));
        //String price = Double.toString(orderList.get(position).getDish().getCarte().getPrice())+"kr";
        //textView2.setText(price);

        if(orderList.get(position).getNote() != null) {
            String note = ("- " + orderList.get(position).getNote());
            textView3.setText(note);
        }
        return rowView;
    }
    /*int getPrice(Order order){
        int orderPrice;
        CarteAPI carteAPI = APIClient.getClient().create(CarteAPI.class);
        Call<List<Carte>> call = carteAPI.getAllCarte();
        call.enqueue(new Callback<List<Carte>>() {
            @Override
            public void onResponse(Call<List<Carte>> call, Response<List<Carte>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(context.getApplicationContext(),"Helvete!" , Toast.LENGTH_LONG).show();
                    return;
                }
                List<Carte> carte = response.body();
                for(Carte c : carte){
                    if (c.getDish().getId() == order.getDish().getId()){
                        return c.getPrice();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Carte>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(),"Network error, cannot reach DB." , Toast.LENGTH_LONG).show();
            }
        });
    }*/
}