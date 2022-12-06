package estgoh.tam.taniaines.tennis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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

        gAdapter = new GameDBAdapter(this);
        fillList();

        ListAdapter adapter = new GameAdapter(this, gameList);
        games = findViewById(R.id.listGames);
        games.setAdapter(adapter);
    }


    private void fillList() {
        gAdapter.open();
        cursor = gAdapter.getAllGames();
        if(cursor != null) {
            //preencher lista de jogos
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                //gameList.add(new Game(cursor.getString(2), cursor.getString(3), ));
                cursor.moveToNext();
            }
        }
        gAdapter.close();
    }

    //function to have a return button
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}