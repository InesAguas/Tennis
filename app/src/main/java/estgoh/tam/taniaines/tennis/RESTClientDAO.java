package estgoh.tam.taniaines.tennis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RESTClientDAO implements ClientDAO{

    APIClient api;

    public RESTClientDAO(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api-android-inesaguas.vercel.app")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        api = retrofit.create(APIClient.class);
    }

    @Override
    public void login(String username, String password, loginListener listener) {
        Call<String> call = api.login(username, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch(response.code()) {
                    case 200:
                        //String token = response.body();
                        listener.onSuccess("OK");
                        break;
                    default:
                        listener.onError(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onError("Call error");
            }
        });
    }

    @Override
    public void viewGames(gamesListener listener) {
        Call<Void> call = api.viewGames();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch(response.code()) {
                    case 200:
                        //String token = response.body();
                        listener.onSuccess(response.raw().toString());
                        break;
                    default:
                        listener.onError(response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError("Call error");
            }
        });
    }
}
