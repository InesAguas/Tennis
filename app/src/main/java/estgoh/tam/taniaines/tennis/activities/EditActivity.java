package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class EditActivity extends AppCompatActivity {

    private EditText editTournament, editPlayer1, editPlayer2;
    Button save_btn;
    private ClientDAO api;
    private SharedPreferences sharedPreferences;
    private String token;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        game = (Game)b.getSerializable("game");

        api = new RESTClientDAO(this);
        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Editing Live Game");

        editTournament = findViewById(R.id.editTournament);
        editPlayer1 = findViewById(R.id.editPlayer1);
        editPlayer2 = findViewById(R.id.editPlayer2);
        save_btn = findViewById(R.id.save_button);

        editTournament.setText(game.getTournament());
        editPlayer1.setText(game.getPlayer1());
        editPlayer2.setText(game.getPlayer2());

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editGame();
            }
        });
    }

    //function called when the user presses the "save" button
    //verifies if the fields are not empty, and sends data to the previous activity
    private void editGame() {
        if(editTournament.getText().toString().matches("") || editPlayer1.getText().toString().matches("") || editPlayer2.getText().toString().matches("")){
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }else{
            game.setPlayer1(editPlayer1.getText().toString());
            game.setPlayer2(editPlayer2.getText().toString());
            game.setTournament(editTournament.getText().toString());
            api.editGame(token, game.getId(), game, new ClientDAO.editGameListener() {
                @Override
                public void onSuccess(String message) {
                    Intent intent = new Intent();
                    intent.putExtra("game", game);
                    setResult(1,intent);
                    finish();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
                }
            });
        }
    }


    //function to have a return button
    //sends the data received previously so nothing is changed in the game
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(1, getIntent());
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}