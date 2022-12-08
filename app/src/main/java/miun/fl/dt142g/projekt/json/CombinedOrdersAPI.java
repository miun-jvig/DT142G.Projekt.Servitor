package miun.fl.dt142g.projekt.json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CombinedOrdersAPI {
    @GET("orders/booking")
    Call<List<List<Object>>> getAllCombinedOrders(@Query("id") int id);

    @GET("orders/kitchen")
    Call<List<CombinedOrders>> getKOrders();
}
