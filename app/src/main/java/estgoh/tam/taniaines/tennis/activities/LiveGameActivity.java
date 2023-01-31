package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.classes.Game;

public class LiveGameActivity extends AppCompatActivity {

    private TextView teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_game);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        Game game = (Game)b.getSerializable("game");

        teste = findViewById(R.id.teste);

        teste.setText(game.getPlayer1());
        //mostrar informacoes jogo etc...
    }
}