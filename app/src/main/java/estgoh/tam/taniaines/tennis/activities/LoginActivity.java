package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.classes.User;
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

        Intent i = getIntent();
        Bundle b = i.getExtras();

        login = findViewById(R.id.loginButton);
        createAcc = findViewById(R.id.newAccButton);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);

        api = new RESTClientDAO(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().matches("") || username.getText().toString().matches("")) {
                    Toast.makeText(view.getContext(), "You have entered an empty string", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username.getText().toString(), password.getText().toString());
                    //HashMap<String, String> map = new HashMap<>();
                    //map.put("username", username.getText().toString());
                    //map.put("password", password.getText().toString());
                    api.login(user, new ClientDAO.loginListener() {
                        @Override
                        public void onSuccess(String token) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", user.getUsername());
                            editor.putString("password", user.getPassword());
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
                if(username.getText().toString().matches("") || password.getText().toString().matches("")
                        || !username.getText().toString().matches("^[a-z0-9]+$")) {
                    Toast.makeText(view.getContext(), "You have entered an empty string or username input has invalid characters", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username.getText().toString(), password.getText().toString());
                    //HashMap<String, String> map = new HashMap<>();
                    //map.put("username", username.getText().toString());
                    //map.put("password", password.getText().toString());
                    api.createAccount(user, new ClientDAO.createAccountListener() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}