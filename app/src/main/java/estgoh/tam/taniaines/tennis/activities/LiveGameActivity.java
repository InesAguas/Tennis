package estgoh.tam.taniaines.tennis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.classes.User;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class LiveGameActivity extends AppCompatActivity {

    private TextView tournament, set, p1, p2, setscore, pl1, pl2, p1s1, p1s2, p1s3, p2s1, p2s2, p2s3, sc1, sc2;
    private Handler handler;
    ClientDAO api;
    private SharedPreferences sharedPreferences;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_game);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        game = (Game)b.getSerializable("game");
        tournament = findViewById(R.id.tournamentLive); //ok
        set = findViewById(R.id.setLive);
        p1 = findViewById(R.id.p1Live); //ok
        p2 = findViewById(R.id.p2Live); //ok
        setscore = findViewById(R.id.scoreLive);
        pl1 = findViewById(R.id.p1Live2); //ok
        pl2 = findViewById(R.id.p2Live2); //ok
        p1s1 = findViewById(R.id.p1set1);
        p1s2 = findViewById(R.id.p1set2);
        p1s3 = findViewById(R.id.p1set3);
        p2s1 = findViewById(R.id.p2set1);
        p2s2 = findViewById(R.id.p2set2);
        p2s3 = findViewById(R.id.p2set3);
        sc1 = findViewById(R.id.p1score);
        sc2 = findViewById(R.id.p2score);

        tournament.setText(game.getTournament());
        set.setText("Set " + game.currentSet());
        p1.setText(game.getPlayer1());
        pl1.setText(game.getPlayer1());
        p2.setText(game.getPlayer2());
        pl2.setText(game.getPlayer2());
        sc1.setText(game.getPoints1());
        sc2.setText(game.getPoints2());

        p1s1.setText(game.getSetScore1(0) + "");
        p1s2.setText(game.getSetScore1(1) + "");
        p1s3.setText(game.getSetScore1(2) + "");

        p2s1.setText(game.getSetScore2(0) + "");
        p2s2.setText(game.getSetScore2(1) + "");
        p2s3.setText(game.getSetScore2(2) + "");

        //sc1 e sc2... meter na BD
        api = new RESTClientDAO(this);
        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);

        handler = new Handler();
        handler.postDelayed(runnable, 5000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = sharedPreferences.getString("token", "");
            api.getUpdates(token, game.getId(), game.getStage(), new ClientDAO.getUpdatesListener() {
                @Override
                public void onSuccess(Game tempGame) {
                    if(tempGame != null) {
                        game.setScore1(tempGame.getScore1());
                        game.setScore2(tempGame.getScore2());
                        game.setPoints1(tempGame.getPoints1());
                        game.setPoints2(tempGame.getPoints2());
                        updateScores();
                    }
                }

                @Override
                public void onError(String message) {
                }
            });

            handler.postDelayed(this, 10000);
        }
    };

    private void updateScores() {
        set.setText("Set " + game.currentSet());

        p1s1.setText(game.getSetScore1(0) + "");
        p1s2.setText(game.getSetScore1(1) + "");
        p1s3.setText(game.getSetScore1(2) + "");

        p2s1.setText(game.getSetScore2(0) + "");
        p2s2.setText(game.getSetScore2(1) + "");
        p2s3.setText(game.getSetScore2(2) + "");

        sc1.setText(game.getPoints1());
        sc2.setText(game.getPoints2());
    }

}