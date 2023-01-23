package estgoh.tam.taniaines.tennis;

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

    @FormUrlEncoded
    @POST("/user/login")
    Call<String> login(@Field("username") String username, @Field("password") String password);

    @Headers("token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NiwiZXhwaXJhdGlvbiI6IjIwMjMtMDEtMjMgMTI6MDI6MzMuMzc4MjYwIn0.FZPPXeICzecw-OP6y-FyNkyI3NcCZJgVLTxNaTcq_CU")
    @GET("/games/all")
    Call<Void> viewGames();

}
