package estgoh.tam.taniaines.tennis.others;

import com.google.gson.JsonObject;

import org.json.JSONObject;

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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIClient {
    @GET("/")
    Call<Void> testing();

    @POST("/user/login")
    Call<User> login(@Body HashMap<String, String> parameters);

    @POST("/user/register")
    Call<User> createAccount(@Body HashMap<String, String> parameters);

    @POST("/games/add")
    Call<Game> addGame(@Header ("token") String token, @Body Game game);

    @GET("/games/all")
    Call<List<Game>> viewGames(@Header ("token") String token);

    @DELETE("/games/{id}/delete")
    Call<Void> deleteGame(@Header("token") String token, @Path("id") int id);

    @PUT("/games/{id}/update")
    Call<Void> updateGame(@Header("token") String token, @Path("id") int id, @Body Game game);

    @GET("games/{id}/{stage}/update")
    Call<Game> getUpdates(@Header("token") String token, @Path("id") int id, @Path("stage") int stage);


}
