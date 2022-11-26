package miun.fl.dt142g.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrderListAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final String[][] values;

    public OrderListAdapter(Context context, String[][] values) {
        super(context, R.layout.activity_order_list_view, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_order_list_view, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.dish_name);
        TextView textView2 = (TextView) rowView.findViewById(R.id.dish_note);
        TextView textView3 = (TextView) rowView.findViewById(R.id.dish_price);
        textView1.setText(values[position][0]);
        textView2.setText(values[position][1]);
        textView3.setText(values[position][2]);
        return rowView;
    }
}