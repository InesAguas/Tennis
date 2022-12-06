package estgoh.tam.taniaines.tennis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {
    public static final String USERNAME = "username";
    private SharedPreferences sharedPreferences;
    private TextView username;
    private Button saveUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings");

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);
        username = findViewById(R.id.username);
        saveUsername = findViewById(R.id.buttonSave);


        if (sharedPreferences != null) {
            String str = sharedPreferences.getString("username", "");
            if (str != null && !str.isEmpty()) {
                username.setText(str);
            }

        }
        saveUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user  = username.getText().toString();
                if(TextUtils.isEmpty(user) || user.equals(" ")){
                    username.setError("Please fill out this field!");
                    return;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME, user);
                editor.commit();
                Toast.makeText(PreferencesActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}