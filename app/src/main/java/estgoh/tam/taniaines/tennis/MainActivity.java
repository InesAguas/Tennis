package estgoh.tam.taniaines.tennis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start, viewGames;
    private EditText player1, player2, tournament;

    public static ArrayList<Game> games = new ArrayList<Game>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tennis Scores");

        start = findViewById(R.id.buttonStart);
        viewGames = findViewById(R.id.buttonViewGames);
        player1 = findViewById(R.id.player1Name);
        player2 = findViewById(R.id.player2Name);
        tournament = findViewById(R.id.tournamentName);
        start.setOnClickListener(this);
        viewGames.setOnClickListener(this);
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
                //tem de ir parar a activity de ver as informações
                return true;
            case R.id.preferences:
                //vai a activity das prefencias e etc
                return true;
            default:
                return false;
        }
    }
}