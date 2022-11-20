package miun.fl.dt142g.projekt;

import android.widget.Button;

public class Table {
    private int id;
    private boolean status;

    Table(int new_id){
        id = new_id;
        status = false;
    }

    public void setStatus(boolean set_status){
        status = set_status;
    }

    public int getID(){
        return id;
    }
    public boolean getStatus(){
        return status;
    }
}
