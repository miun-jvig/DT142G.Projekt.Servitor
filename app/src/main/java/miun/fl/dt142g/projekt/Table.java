package miun.fl.dt142g.projekt;


import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private boolean status;
    private Booking booking;

    Table(int id){
        this.id = id;
        status = false;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    public void setBooking(Booking booking){
        this.booking = booking;
    }
    public Booking getBooking(){return booking;}

    public int getID(){
        return id;
    }
    public boolean getStatus(){
        return status;
    }
}
