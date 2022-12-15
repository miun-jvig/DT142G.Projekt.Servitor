package miun.fl.dt142g.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListAdapter extends ArrayAdapter<OrderContainer>{

    private final Context context;
    private final ArrayList<OrderContainer> orderList;

    public OrderListAdapter(Context context, ArrayList<OrderContainer> order) {
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
    // Sets Name
        textView1.setText(orderList.get(position).getOrder().getDish().getName());
    // Sets Price
        String price = orderList.get(position).getCarte().getPrice()+"kr";
        textView2.setText(price);
    //Sets Note
        if(orderList.get(position).getOrder().getNote() != null) {
            String note = ("- " + orderList.get(position).getOrder().getNote());
            textView3.setText(note);
        }
        return rowView;
    }
}