package miun.fl.dt142g.projekt.json;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DishAPI {
    @GET("dish")
    Call<List<Dish>> getAllDish();
}
