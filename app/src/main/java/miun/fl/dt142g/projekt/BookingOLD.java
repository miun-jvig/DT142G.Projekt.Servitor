package miun.fl.dt142g.projekt;

import java.io.Serializable;
import java.util.Date;

public class BookingOLD implements Serializable {
    private String note;
    private String name;
    private Date time;
    private String amount;

    BookingOLD(String name, String amount, String note){
        this.note = note;
        this.name = name;
//        this.time = time;
        this.amount = amount;
    }

}
