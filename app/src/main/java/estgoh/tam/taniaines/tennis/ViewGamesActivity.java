package estgoh.tam.taniaines.tennis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ViewGamesActivity extends AppCompatActivity {

    ListView games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Games");

        ListAdapter adapter = new GameAdapter(this);
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