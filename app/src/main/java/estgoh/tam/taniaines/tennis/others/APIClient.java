package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;
import java.util.List;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.classes.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIClient {
    @Headers("Accept: application/json")
    @GET("/")
    Call<Void> testing();

    @Headers("Accept: application/json")
    @POST("/user/login")
    Call<User> login(@Body HashMap<String, String> parameters);

    @Headers("Accept: application/json")
    @POST("/games/add")
    Call<Void> addGame(@Header ("token") String token,@Body Game game);

    @Headers("Accept: application/json")
    @GET("/games/all")
    Call<List<Game>> viewGames(@Header ("token") String token);

    @Headers("Accept: application/json")
    @DELETE("/games/{id}/delete")
    Call<Void> deleteGame(@Header("token") String token, @Path("id") int id);


}
