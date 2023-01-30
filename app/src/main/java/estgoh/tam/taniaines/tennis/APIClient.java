package estgoh.tam.taniaines.tennis;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIClient {
    @GET("/")
    Call<Void> testing();

    @POST("/user/login")
    Call<User> login(@Body HashMap<String, String> parameters);

    @GET("/games/all")
    Call<Void> viewGames();

}
