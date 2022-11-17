package miun.fl.dt142g.projekt;

import android.content.Intent;
import android.view.View;

/**
 * A class for switching between activities.
 *
 * Typical usage.
 * <pre>
 *     Private Button button_new = findViewById(R.id.button_id);
 *     Intent new_intent = new Intent(this, otherActivity.class);
 *     button_new.setOnClickListener(new SwitchActivity(new_intent));
 * </pre>
 */
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
