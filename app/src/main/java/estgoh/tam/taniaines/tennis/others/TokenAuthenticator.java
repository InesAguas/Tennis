package estgoh.tam.taniaines.tennis.others;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import estgoh.tam.taniaines.tennis.classes.User;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private User user;
    private Context context;

    public TokenAuthenticator(User user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        ClientDAO api = new RESTClientDAO(user, context);
        api.login(user, new ClientDAO.loginListener() {
            @Override
            public void onSuccess(String token) {
                user.setToken(token);
                SharedPreferences.Editor editor = context.getSharedPreferences("SharedPref",context.MODE_PRIVATE).edit();
                editor.putString("token", token);
                editor.commit();
            }

            @Override
            public void onError(String message) {
                //cenas...
            }
        });

        return response.request().newBuilder()
                .header("token", user.getToken())
                .build();
    }
}