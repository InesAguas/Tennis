package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
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
    Cursor cursor;
    GameDBAdapter gAdapter;
    List<Game> gameList;

    private SharedPreferences sharedPreferences;
    ClientDAO api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Games");

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);
        api = new RESTClientDAO();
        String token = sharedPreferences.getString("token", "");
        api.viewGames(token, new ClientDAO.gamesListener() {
            @Override
            public void onSuccess(List<Game> games) {
                ListAdapter adapter = new GameAdapter(getBaseContext(), games);
                gamesview = findViewById(R.id.listGames);
                gamesview.setAdapter(adapter);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
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