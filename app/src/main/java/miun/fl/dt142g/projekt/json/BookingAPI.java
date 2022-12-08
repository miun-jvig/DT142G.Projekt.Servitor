package miun.fl.dt142g.projekt.json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingAPI {

    @POST("booking")
    Call<Booking> postBooking(@Body Booking booking);

    @GET("booking")
    Call<List<Booking>> getAllBooking();

    @GET("booking")
    Call<List<Booking>> getAllBookingWithDate(@Query("date") String date);

}