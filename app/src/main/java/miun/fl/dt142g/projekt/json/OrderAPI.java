package miun.fl.dt142g.projekt.json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderAPI {
    @POST("orders")
    Call<Order> postOrder(@Body Order order);
}
