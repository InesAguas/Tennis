package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import estgoh.tam.taniaines.tennis.classes.User;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start, viewGames;
    private EditText player1, player2, tournament;
    private SharedPreferences sharedPreferences;
    private TextView welcome;
    private User user;
    ClientDAO api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = new RESTClientDAO(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tennis Scores");

        start = findViewById(R.id.buttonStart);
        viewGames = findViewById(R.id.buttonViewGames);
        player1 = findViewById(R.id.player1Name);
        player2 = findViewById(R.id.player2Name);
        tournament = findViewById(R.id.tournamentName);
        welcome = findViewById(R.id.welcome);

        start.setOnClickListener(this);
        viewGames.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        String pass = sharedPreferences.getString("password", "");

        if(username != null && !username.isEmpty() && pass != null && !pass.isEmpty()) {
            user = new User(username, pass);
            api.login(user, new ClientDAO.loginListener() {
                @Override
                public void onSuccess(String token) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.commit();
                    Toast.makeText(getBaseContext(), "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(String message) {
                    Intent iLogin = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(iLogin);
                }
            });
        } else {
            Intent iLogin = new Intent(this, LoginActivity.class);
            startActivity(iLogin);
        }


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonStart:
                //when the start game button is pressed, verifies that fields are not empty and starts the activity with the info
                if(player1.getText().toString().matches("") || player2.getText().toString().matches("")
                        || tournament.getText().toString().matches("")){
                    Toast.makeText(this, "You have entered an empty string", Toast.LENGTH_SHORT).show();
                }else{
                    Intent iStart = new Intent(this, GameActivity.class);
                    iStart.putExtra("player1", player1.getText().toString());
                    iStart.putExtra("player2", player2.getText().toString());
                    iStart.putExtra("tournament", tournament.getText().toString());
                    startActivity(iStart);
                }
                break;
            case R.id.buttonViewGames:
                //when the view games button is pressed, starts activity
                Intent iViewGames = new Intent(this, ViewGamesActivity.class);
                startActivity(iViewGames);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent iAbout = new Intent(this, AboutActivity.class);
                startActivity(iAbout);
                return true;
            case R.id.preferences:
                Intent iPreferences = new Intent(this, PreferencesActivity.class);
                startActivity(iPreferences);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);

        if (sharedPreferences != null) {
            String str = sharedPreferences.getString("username", "");
            if (str != null && !str.isEmpty()) {
                welcome.setText("\uD83D\uDC4B Welcome, " + str + "!");
            }

        }
    }
}