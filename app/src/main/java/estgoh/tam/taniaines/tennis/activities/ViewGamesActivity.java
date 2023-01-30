package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.others.GameAdapter;
import estgoh.tam.taniaines.tennis.others.GameDBAdapter;
import estgoh.tam.taniaines.tennis.R;

public class ViewGamesActivity extends AppCompatActivity {

    ListView games;
    Cursor cursor;
    GameDBAdapter gAdapter;
    ArrayList<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Games");

        gameList = new ArrayList<Game>();
        gAdapter = new GameDBAdapter(this);
        gAdapter.open();
        cursor = gAdapter.getAllGames();

        if(cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int score1[] = {cursor.getInt(4), cursor.getInt(5), cursor.getInt(6)};
                int score2[] = {cursor.getInt(7), cursor.getInt(8), cursor.getInt(9)};
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = format.parse(cursor.getString(10));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                gameList.add(new Game(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), score1, score2, date));
                cursor.moveToNext();
            }
        }
        gAdapter.close();

        ListAdapter adapter = new GameAdapter(this, gameList);
        games = findViewById(R.id.listGames);
        games.setAdapter(adapter);
    }


    //function to have a return button
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}