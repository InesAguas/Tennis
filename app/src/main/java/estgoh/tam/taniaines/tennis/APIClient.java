package estgoh.tam.taniaines.tennis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIClient {
    @GET("/")
    Call<Void> testing();
}
