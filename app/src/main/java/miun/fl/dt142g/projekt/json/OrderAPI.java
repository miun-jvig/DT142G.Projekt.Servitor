package miun.fl.dt142g.projekt.json;

import android.util.Pair;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderAPI {
    @POST("orders")
    Call<Order> postOrder(@Body Order order);

    @GET("orders/booking")
    Call<List<List<Object>>> getAllCombinedOrders(@Query("id") int id);
}
