package estgoh.tam.taniaines.tennis.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class CreateAccountActivity extends AppCompatActivity {
    private Button createAcc;
    private EditText username, password;
    ClientDAO api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        createAcc = findViewById(R.id.newAccButton);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);

        api = new RESTClientDAO();
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().matches("") || password.getText().toString().matches("")
                || !username.getText().toString().matches("^[a-z0-9]+$")) {
                    Toast.makeText(view.getContext(), "You have entered an empty string or username input has invalid characters", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", username.getText().toString());
                    map.put("password", password.getText().toString());
                    api.createAccount(map, new ClientDAO.createAccountListener() {
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
