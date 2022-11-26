package miun.fl.dt142g.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderListAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final ArrayList<Item> order;

    public OrderListAdapter(Context context, ArrayList<Item> order) {
        super(context, R.layout.activity_order_list_view, order);
        this.context = context;
        this.order = order;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_order_list_view, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.dish_name);
        TextView textView2 = (TextView) rowView.findViewById(R.id.dish_price);
        TextView textView3 = (TextView) rowView.findViewById(R.id.dish_note);
        textView1.setText(order.get(position).getName());
        textView2.setText(Double.toString(order.get(position).getPrice())+"kr");
        textView3.setText("- "+order.get(position).getNote());
        return rowView;
    }
}