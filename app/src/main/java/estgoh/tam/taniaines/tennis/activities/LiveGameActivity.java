package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.classes.Game;

public class LiveGameActivity extends AppCompatActivity {

    private TextView teste;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_game);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        Game game = (Game)b.getSerializable("game");

        teste = findViewById(R.id.teste);
        handler = new Handler();
        handler.postDelayed(runnable, 5000);

        //teste.setText(game.getStage());

        //mostrar informacoes jogo etc...
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Random rand = new Random();
            teste.setText(rand.nextInt(20) + "");
            /* and here comes the "trick" */
            handler.postDelayed(this, 10000);
        }
    };


    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



}