package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.GameAdapter;
import estgoh.tam.taniaines.tennis.others.GameDBAdapter;
import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class ViewGamesActivity extends AppCompatActivity {

    ListView gamesview;
    List<Game> gamelist;
    private SharedPreferences sharedPreferences;
    ClientDAO api;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Games");

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);
        api = new RESTClientDAO();
        token = sharedPreferences.getString("token", "");
        api.viewGames(token, new ClientDAO.gamesListener() {
            @Override
            public void onSuccess(List<Game> games) {
                gamelist = games;
                startAdap();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void startAdap() {
        ListAdapter adapter = new GameAdapter(this, gamelist, token);
        gamesview = findViewById(R.id.listGames);
        gamesview.setAdapter(adapter);
        gamesview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent iliveGame = new Intent(view.getContext(), LiveGameActivity.class);
                if(gamelist.get(i).getStage() != 0) {
                    iliveGame.putExtra("game", gamelist.get(i));
                    startActivity(iliveGame);
                }
            }
        });
    }

    //function to have a return button
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}