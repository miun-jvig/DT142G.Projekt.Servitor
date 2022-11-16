package miun.fl.dt142g.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TablesActivity extends AppCompatActivity {
    private Button button_table1, button_table2, Button_table3, button_table4, button_table5, button_table6, button_table7; //Tablebuttons
    private int current_table = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        //button_table1 = findViewById(R.id.button_table1);

    }
    public void onClick(View view){
        TextView text = findViewById(R.id.textViewTemp);
        switch(view.getId()) {
            case R.id.button_table1:
                current_table = 1;
            break;
            case R.id.button_table2:
                current_table = 2;
            break;
            case R.id.button_table3:
                current_table = 3;
            break;
            case R.id.button_table4:
                current_table = 4;
            break;
            case R.id.button_table5:
                current_table = 5;
            break;
            case R.id.button_table6:
                current_table = 6;
            break;
            case R.id.button_table7:
                current_table = 7;
            break;
        }
        text.setText("Valt bord: "+current_table);
    }
}