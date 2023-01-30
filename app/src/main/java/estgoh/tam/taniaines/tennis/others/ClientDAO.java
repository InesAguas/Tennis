package estgoh.tam.taniaines.tennis.others;

import java.util.HashMap;

public interface ClientDAO {

    public interface loginListener {
        public void onSuccess(String token);
        public void onError(String message);
    }
    public void login(HashMap<String, String> params, loginListener listener);

    public interface gamesListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void viewGames(gamesListener listener);
}
