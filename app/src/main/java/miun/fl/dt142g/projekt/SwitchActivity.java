package miun.fl.dt142g.projekt;

import android.content.Intent;
import android.view.View;

public class SwitchActivity implements View.OnClickListener {
    Intent new_activity;

    SwitchActivity(Intent activity){
        new_activity = activity;
    }
    private void nextActivity(View view) {
        view.getContext().startActivity(new_activity);
    }
    @Override
    public void onClick(View view){
        nextActivity(view);
    }
}
