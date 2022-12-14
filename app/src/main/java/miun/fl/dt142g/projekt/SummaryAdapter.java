package miun.fl.dt142g.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import miun.fl.dt142g.projekt.json.CombinedOrders;

import java.util.ArrayList;

public class SummaryAdapter extends ArrayAdapter<CombinedOrders>{

    private final Context context;
    private final ArrayList<CombinedOrders> orderList;

    public SummaryAdapter(Context context, ArrayList<CombinedOrders> orders) {
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

        String name = orderList.get(position).getDish().getName();
        int price = orderList.get(position).getPrice();
        textView1.setText(name);
        textView2.setText(Integer.toString(price));

        if(orderList.get(position).getNotes() != null) {
            String note = orderList.get(position).getNotes();
            textView3.setText(note);
        }
        return rowView;
    }
}