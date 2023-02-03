package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;
import java.util.List;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.classes.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientDAO {

    public interface loginListener {
        public void onSuccess(String token);
        public void onError(String message);
    }
    public void login(User user, loginListener listener);

    public interface createAccountListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void createAccount(User user, createAccountListener listener);

    public interface userEditListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void editUser(String token, User user, userEditListener listener);

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
    public void updateGame(String token, int id,Game game, updateGameListener listener);

    public interface getUpdatesListener {
        public void onSuccess(Game game);
        public void onError(String message);
    }
    public void getUpdates(String token, int id, int stage, getUpdatesListener listener);

    public interface editGameListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void editGame(String token, int id, Game game, editGameListener listener);

}
