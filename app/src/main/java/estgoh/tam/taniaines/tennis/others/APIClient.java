package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.classes.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIClient {
    @GET("/")
    Call<Void> testing();

    @POST("/user/login")
    Call<User> login(@Body HashMap<String, String> parameters);

    @POST("/user/register")
    Call<User> createAccount(@Body HashMap<String, String> parameters);

    @POST("/games/add")
    Call<Void> addGame(@Header ("token") String token,@Body Game game);

    @GET("/games/all")
    Call<Void> viewGames();

}
