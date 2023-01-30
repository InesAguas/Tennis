package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;

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
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void addGame(String token, Game game, addGameListener listener);

    public interface gamesListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void viewGames(gamesListener listener);
}
