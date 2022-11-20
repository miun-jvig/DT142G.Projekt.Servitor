package miun.fl.dt142g.projekt;


import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private boolean status;
    private Booking booking;

    Table(int new_id){
        id = new_id;
        status = false;
    }

    public void setStatus(boolean set_status){
        status = set_status;
    }
    public void setBooking(Booking new_booking){
        booking = new_booking;
    }

    public int getID(){
        return id;
    }
    public boolean getStatus(){
        return status;
    }
}
