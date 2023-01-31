package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import estgoh.tam.taniaines.tennis.R;

public class LiveGameActivity extends AppCompatActivity {

    private TextView teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_game);

        teste = findViewById(R.id.teste);

        //mostrar informacoes jogo etc...

        

    }
}