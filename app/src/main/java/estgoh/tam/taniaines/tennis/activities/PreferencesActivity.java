package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import estgoh.tam.taniaines.tennis.R;

public class PreferencesActivity extends AppCompatActivity {
    public static final String USERNAME = "username";
    private SharedPreferences sharedPreferences;
    private TextView username;
    private Button saveUsername, logout;

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
        logout = findViewById(R.id.buttonLogout);


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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreferencesActivity.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("username");
                                editor.remove("password");
                                editor.remove("token");
                                editor.commit();

                                Intent login = new Intent(PreferencesActivity.this, LoginActivity.class);
                                startActivity(login);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
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