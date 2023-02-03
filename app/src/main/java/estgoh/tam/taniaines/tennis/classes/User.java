package estgoh.tam.taniaines.tennis.classes;

import android.content.SharedPreferences;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String token, username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //esta funcao pode fazer pedido a API para dar reset ao token...
    public void resetToken() {

    }
}
