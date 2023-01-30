package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.classes.User;
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
    public void login(HashMap<String, String> params, loginListener listener) {
        Call<User> call = api.login(params);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch(response.code()) {
                    case 200:
                        User user = response.body();
                        listener.onSuccess(user.getToken());
                        break;
                    case 400:
                        listener.onError("Wrong credentials");
                        break;
                    case 500:
                        listener.onError("Server Error");
                        break;
                    default:
                        listener.onError("Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onError("Call error");
            }

        });
    }

    @Override
    public void addGame(String token, Game game, addGameListener listener) {
        Call<Void> call = api.addGame(token,game);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch(response.code()) {
                    case 200:
                        listener.onSuccess("Game saved");
                        break;
                    case 400:
                        listener.onError("Parameters missing");
                        break;
                    case 500:
                        listener.onError("Server Error");
                        break;
                    default:
                        listener.onError("Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
