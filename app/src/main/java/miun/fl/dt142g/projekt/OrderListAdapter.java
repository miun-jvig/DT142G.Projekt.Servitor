package miun.fl.dt142g.projekt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import miun.fl.dt142g.projekt.json.Carte;

public class OrderListAdapter extends ArrayAdapter<Carte>{

    private final Context context;
    private final ArrayList<Carte> order;

    public OrderListAdapter(Context context, ArrayList<Carte> order) {
        super(context, R.layout.activity_order_list_view, order);
        this.context = context;
        this.order = order;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_order_list_view, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.dish_name);
        TextView textView2 = (TextView) rowView.findViewById(R.id.dish_price);
        TextView textView3 = (TextView) rowView.findViewById(R.id.dish_note);
        Button deleteButton = (Button) rowView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            order.remove(position);
            notifyDataSetChanged();
        });
        textView1.setText(order.get(position).getDish().getName());
        textView2.setText(Double.toString(order.get(position).getPrice())+"kr");
        //textView3.setText("- "+order.get(position).getNote());
        return rowView;
    }
}