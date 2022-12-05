package estgoh.tam.taniaines.tennis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private EditText editTournament, editPlayer1, editPlayer2;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Editing Live Game");

        editTournament = findViewById(R.id.editTournament);
        editPlayer1 = findViewById(R.id.editPlayer1);
        editPlayer2 = findViewById(R.id.editPlayer2);
        save_btn = findViewById(R.id.save_button);

        editTournament.setText(b.getString("tournament"));
        editPlayer1.setText(b.getString("player1"));
        editPlayer2.setText(b.getString("player2"));

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
            Intent intent = new Intent();
            intent.putExtra("tournament", editTournament.getText().toString());
            intent.putExtra("player1", editPlayer1.getText().toString());
            intent.putExtra("player2", editPlayer2.getText().toString());
            setResult(1,intent);
            finish();
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