package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;
import java.util.List;

import estgoh.tam.taniaines.tennis.classes.Game;

public interface ClientDAO {

    public interface loginListener {
        public void onSuccess(String token);
        public void onError(String message);
    }
    public void login(HashMap<String, String> params, loginListener listener);

    public interface createAccountListener {
        public void onSuccess(String message);
        public void onError(String message);
    }

    public void createAccount(HashMap<String, String> params, createAccountListener listener);

    public interface addGameListener {
        public void onSuccess(int id);
        public void onError(String message);
    }
    public void addGame(String token, Game game, addGameListener listener);

    public interface gamesListener {
        public void onSuccess(List<Game> games);
        public void onError(String message);
    }
    public void viewGames(String token, gamesListener listener);

    public interface deleteGameListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void deleteGame(String token, int id, deleteGameListener listener);

    public interface updateGameListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void updateGame(String token, int id, int[] score1, int[] score2, int stage, updateGameListener listener);

    public interface getUpdatesListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void getUpdates(String token, int id, int stage, getUpdatesListener listener);
}
