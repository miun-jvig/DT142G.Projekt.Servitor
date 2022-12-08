package miun.fl.dt142g.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import miun.fl.dt142g.projekt.json.APIClient;
import miun.fl.dt142g.projekt.json.Booking;
import miun.fl.dt142g.projekt.json.Carte;
import miun.fl.dt142g.projekt.json.CarteAPI;
import miun.fl.dt142g.projekt.json.CombinedOrders;
import miun.fl.dt142g.projekt.json.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryAdapter extends ArrayAdapter<List<Object>>{

    private final Context context;
    private final ArrayList<List<Object>> orderList;

    public SummaryAdapter(Context context, ArrayList<List<Object>> orders) {
        super(context, R.layout.activity_summary_list_view, orders);
        this.context = context;
        this.orderList = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_summary_list_view, parent, false);
        TextView textView1 = rowView.findViewById(R.id.dish_name);
        TextView textView2 = rowView.findViewById(R.id.dish_price);
        TextView textView3 = rowView.findViewById(R.id.dish_note);

        LinkedTreeMap orderL = (LinkedTreeMap) orderList.get(position).get(0);
        LinkedTreeMap carteL = (LinkedTreeMap) orderList.get(position).get(1);

        LinkedTreeMap dish = (LinkedTreeMap) orderL.get("dish");
        textView1.setText((String) dish.get("name"));


        String price = Double.toString((Double)carteL.get("price"))+"kr";
        textView2.setText(price);

        if(orderL.get("notes") != null) {
            String note = ("- " + orderL.get("notes"));
            textView3.setText(note);
        }
        return rowView;
    }
}