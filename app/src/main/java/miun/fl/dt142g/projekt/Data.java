//package miun.fl.dt142g.projekt;
//
//import java.util.List;
//
//import miun.fl.dt142g.projekt.json.Employee;
//import miun.fl.dt142g.projekt.json.EmployeeAPI;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class Data {
//    private final HttpLoggingInterceptor loggingInterceptor;
//    private final OkHttpClient okHttpClient;
//    private final String DB;
//    private final Retrofit retrofit;
//
//    Data(){
//        DB = "10.82.231.15";
//        loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://" + DB + ":8080/antons-skafferi-db-1.0-SNAPSHOT/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    List<Object> getSpecificItem(String userInput) {
//        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
//        Call<List<Employee>> call = employeeAPI.getEmployeeWithId(userInput);
//        call.enqueue(new Callback<List<Employee>>() {
//            @Override
//            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
//                if (!response.isSuccessful()) {
//                    // dåligt svar
//                    return;
//                }
//                List<Employee> employee = response.body();
//                Employee currentEmployee = employee.get(0);
//                if (!employee.isEmpty()) {
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Employee>> call, Throwable t) {
//
//            }
//        });
//    }
//}
