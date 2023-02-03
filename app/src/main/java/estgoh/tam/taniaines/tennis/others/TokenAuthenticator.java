package estgoh.tam.taniaines.tennis.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.IOException;

import estgoh.tam.taniaines.tennis.classes.User;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private Context context;
    private ClientDAO api;
    private SharedPreferences sharedPreferences;

    public TokenAuthenticator(Context context, RESTClientDAO api) {
        this.context = context;
        this.api = api;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        sharedPreferences = context.getSharedPreferences("SharedPref",context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String pass = sharedPreferences.getString("password", "");
        User user = new User(username, pass);
        api.login(user, new ClientDAO.loginListener() {
            @Override
            public void onSuccess(String token) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", token);
                editor.commit();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        return response.request().newBuilder()
                .header("token", sharedPreferences.getString("token", ""))
                .build();
    }
}