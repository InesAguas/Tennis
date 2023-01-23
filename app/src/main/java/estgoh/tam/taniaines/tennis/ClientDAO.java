package estgoh.tam.taniaines.tennis;

public interface ClientDAO {

    public interface loginListener {
        public void onSuccess(String token);
        public void onError(String message);
    }
    public void login(String username, String password, loginListener listener);

    public interface gamesListener {
        public void onSuccess(String message);
        public void onError(String message);
    }
    public void viewGames(gamesListener listener);
}
