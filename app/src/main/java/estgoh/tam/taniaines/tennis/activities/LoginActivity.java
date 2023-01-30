package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class LoginActivity extends AppCompatActivity {

    private Button login, createAcc;
    private EditText username, password;
    private SharedPreferences sharedPreferences;
    ClientDAO api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginButton);
        createAcc = findViewById(R.id.newAccButton);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);

        api = new RESTClientDAO();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().matches("") || username.getText().toString().matches("")) {
                    Toast.makeText(view.getContext(), "You have entered an empty string", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", username.getText().toString());
                    map.put("password", password.getText().toString());
                    api.login(map, new ClientDAO.loginListener() {
                        @Override
                        public void onSuccess(String token) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", map.get("username"));
                            editor.putString("password", map.get("password"));
                            editor.putString("token", token);
                            editor.commit();
                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir activity nova ou usar dados desta pra criar conta??
            }
        });
    }
}