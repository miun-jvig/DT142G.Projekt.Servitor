package miun.fl.dt142g.projekt;


import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private boolean status;
    private BookingOLD booking;

    Table(int id){
        this.id = id;
        status = false;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    public void setBooking(BookingOLD booking){
        this.booking = booking;
    }
    public BookingOLD getBooking(){return booking;}

    public int getID(){
        return id;
    }
    public boolean getStatus(){
        return status;
    }
}
