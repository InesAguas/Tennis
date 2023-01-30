package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.classes.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIClient {
    @GET("/")
    Call<Void> testing();

    @POST("/user/login")
    Call<User> login(@Body HashMap<String, String> parameters);

    @GET("/games/all")
    Call<Void> viewGames();

}
