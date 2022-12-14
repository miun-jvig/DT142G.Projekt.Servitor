package miun.fl.dt142g.projekt.json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import miun.fl.dt142g.projekt.json.CombinedOrders;

public interface CombinedOrdersAPI {
    @GET("orders/booking")
    Call<List<CombinedOrders>> getAllCombinedOrders(@Query("id") int id);
}
