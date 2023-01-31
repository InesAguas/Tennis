package estgoh.tam.taniaines.tennis.others;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

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
    public void createAccount(HashMap<String, String> params, createAccountListener listener) {
        Call<User> call = api.createAccount(params);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch(response.code()) {
                    case 200:
                        listener.onSuccess("Account created successfully");
                        break;
                    case 400:
                        listener.onError("User already exists");
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
                listener.onError("Error creating account");
            }

        });
    }


    @Override
    public void addGame(String token, Game game, addGameListener listener) {
        Call<Game> call = api.addGame(token,game);
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                switch(response.code()) {
                    case 200:
                        Game temp = response.body();
                        listener.onSuccess(temp.getId());
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
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onError("Call error");
            }
        });
    }

    @Override
    public void viewGames(String token, gamesListener listener) {
        Call<List<Game>> call = api.viewGames(token);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                switch(response.code()) {
                    case 200:
                        List<Game> games = response.body();
                        if(games == null){
                            listener.onError("Null list");
                            return;
                        }
                        listener.onSuccess(games);
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
            public void onFailure(Call<List<Game>> call, Throwable t) {
                listener.onError("Call error");
            }
        });
    }

    @Override
    public void deleteGame(String token, int id, deleteGameListener listener) {
        Call<Void> call = api.deleteGame(token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch(response.code()) {
                    case 200:
                        listener.onSuccess("Game deleted");
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
    public void updateGame(String token, int id, int[] score1, int[] score2, int stage, updateGameListener listener) {
        Call<Void> call = api.updateGame(token, id, score1, score2, stage);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void getUpdates(String token, int id, int stage, getUpdatesListener listener) {
        Call<Void> call = api.getUpdates(token, id, stage);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch(response.code()) {
                    case 200:
                        //aqui tem de enviar os dados novos
                        listener.onSuccess("");
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

            }
        });
    }
}
