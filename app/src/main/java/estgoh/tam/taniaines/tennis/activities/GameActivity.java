package estgoh.tam.taniaines.tennis.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.others.ClientDAO;
import estgoh.tam.taniaines.tennis.others.GameDBAdapter;
import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.others.RESTClientDAO;

public class GameActivity extends AppCompatActivity{

    private TextView tournament, player1, player2, pt_player1, pt_player2, set1_p1,set1_p2,set2_p1,set2_p2,set3_p1,set3_p2,name_player1, name_player2;
    private Button btn_player1, btn_player2, btn_end;
    private int num_set;
    private TextView setNum;
    private int[] score1 = {0, 0, 0};
    private int[] score2 = {0, 0, 0};

    private Game game;
    private String token;

    private SharedPreferences sharedPreferences;
    GameDBAdapter gAdapter;
    ClientDAO api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Live Game");

        sharedPreferences = getSharedPreferences("SharedPref",MODE_PRIVATE);
        gAdapter = new GameDBAdapter(this);
        api = new RESTClientDAO();

        tournament = findViewById(R.id.tourName);
        player1 = findViewById(R.id.p1Name);
        player2 = findViewById(R.id.p2Name);
        btn_player1= findViewById(R.id.p1name_button);
        btn_player2= findViewById(R.id.p2name_button);
        pt_player1 = findViewById(R.id.p1_pt);
        pt_player2= findViewById(R.id.p2_pt);
        name_player1 = findViewById(R.id.textView5);
        name_player2 = findViewById(R.id.textView7);
        set1_p1 = findViewById(R.id.set1_p1);
        set1_p2= findViewById(R.id.set1_p2);
        set2_p1 = findViewById(R.id.set2_p1);
        set2_p2= findViewById(R.id.set2_p2);
        set3_p1 = findViewById(R.id.set3_p1);
        set3_p2 = findViewById(R.id.set3_p2);
        setNum = findViewById(R.id.setNum);
        btn_end = findViewById(R.id.end_button);

        tournament.setText(b.getString("tournament"));
        player1.setText(b.getString("player1"));
        player2.setText(b.getString("player2"));
        name_player1.setText(b.getString("player1"));
        name_player2.setText(b.getString("player2"));

        num_set = 1;
        token = sharedPreferences.getString("token", "");
        startGame();

        //listener for the score button of player 1
        btn_player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1Scores();
            }
        });

        //listener for the score button of player 2
        btn_player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player2Scores();
            }
        });

        //listener for the end game button
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    //function to change score numbers when player 1 scores
    private void player1Scores() {
        if(score1[num_set-1] == 6 && score2[num_set-1] == 6) {
            int i = Integer.parseInt(pt_player1.getText().toString());
            i++;
            pt_player1.setText(i + "");
            int j = Integer.parseInt(pt_player2.getText().toString());

            if(i >= 7 && (j <= i-2)) {
                changeScore("p1");
            }
        } else {
            if (pt_player1.getText().equals("0")){
                pt_player1.setText("15");
            }else if (pt_player1.getText().equals("15")){
                pt_player1.setText("30");
            }else if (pt_player1.getText().equals("30")){
                pt_player1.setText("40");
            }else if (pt_player1.getText().equals("40")) {
                if (pt_player2.getText().equals("40")) {
                    pt_player1.setText("AD");
                } else if (pt_player2.getText().equals("AD")) {
                    pt_player2.setText("40");
                }else{
                    changeScore("p1");
                }
            }else if (pt_player1.getText().equals("AD") && pt_player2.getText().equals("40")) {
                changeScore("p1");
            }
        }
    }

    //function to change score numbers when player 2 scores
    private void player2Scores() {
        if(score1[num_set-1] == 6 && score2[num_set-1] == 6) {
            int i = Integer.parseInt(pt_player2.getText().toString());
            i++;
            pt_player2.setText(i + "");

            int j = Integer.parseInt(pt_player1.getText().toString());
            if(i >= 7 && (j <= i-2)) {
                changeScore("p2");
            }
        } else {
            if (pt_player2.getText().equals("0")){
                pt_player2.setText("15");
            }else if (pt_player2.getText().equals("15")){
                pt_player2.setText("30");
            }else if (pt_player2.getText().equals("30")){
                pt_player2.setText("40");
            }else if (pt_player2.getText().equals("40")) {
                if (pt_player1.getText().equals("40")) {
                    pt_player2.setText("AD");
                } else if (pt_player1.getText().equals("AD")) {
                    pt_player1.setText("40");
                }else{
                    changeScore("p2");
                }
            }else if (pt_player2.getText().equals("AD") && pt_player1.getText().equals("40")) {
                changeScore("p2");
            }
        }
    }

    //function to change set scores when a player wins a game
    private void changeScore(String player) {
        pt_player1.setText("0");
        pt_player2.setText("0");

        if(player.equals("p1")) {
            score1[num_set-1] += 1;
            update();
            set1_p1.setText(score1[0] + "");
            set2_p1.setText(score1[1] + "");
            set3_p1.setText(score1[2] + "");

            if((score1[num_set-1] == 6 && score2[num_set-1] <= 4) || (score1[num_set-1] == 7)) {
                congratsMessage_set(player1.getText());
                if(!winnerExists()) {
                    num_set++;
                    setNum.setText("Set " + num_set);
                } else {
                    saveGame();
                }
            }
        } else {
            score2[num_set-1] += 1;
            update();
            set1_p2.setText(score2[0] + "");
            set2_p2.setText(score2[1] + "");
            set3_p2.setText(score2[2] + "");
            if((score2[num_set-1] == 6 && score1[num_set-1] <= 4) || (score2[num_set-1] == 7)) {
                congratsMessage_set(player2.getText());
                if(!winnerExists()) {
                    num_set++;
                    setNum.setText("Set " + num_set);
                } else {
                    saveGame();
                }
            }
        }

        if(score1[num_set-1] == 6 && score2[num_set-1] == 6) {
            setNum.setText("Set " + num_set + "\nTie Break");
        }
    }

    //function to save the game
    private void saveGame(){
        game.setScore1(score1);
        game.setScore2(score2);
        game.setStage(0);

        api.updateGame(token, game.getId(), game, new ClientDAO.updateGameListener() {
            @Override
            public void onSuccess(String message) {
                AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this, R.style.CustomMaterialDialog);
                alert.setTitle("Game saved");
                if(game.getWinner() == 1) {
                    alert.setMessage("Congratulations, " + game.getPlayer1() + " you won the game. The game was automatically saved.");
                } else {
                    alert.setMessage("Congratulations, " + game.getPlayer2() + " you won the game. The game was automatically saved.");
                }
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                alert.show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void startGame() {
        int[] sc1 = {0,0,0};
        int[] sc2 = {0,0,0};
        game = new Game(tournament.getText().toString(), player1.getText().toString(), player2.getText().toString(), sc1, sc2, new Date());
        api.addGame(token, game, new ClientDAO.addGameListener() {
            @Override
            public void onSuccess(int id) {
                game.setId(id);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void update() {
        game.setScore1(score1);
        game.setScore2(score2);
        game.setStage(game.getStage() + 1);
        api.updateGame(token, game.getId(), game, new ClientDAO.updateGameListener() {
            @Override
            public void onSuccess(String message) {
                //nao faz nada...
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //function to show a dialogue message congratulating the winner of the set
    //this dialogue is only informative, the button "OK" doesn't do anything other than close the dialogue
    private void congratsMessage_set(CharSequence player) {
        String num = "";

        if(num_set == 1){
            num = "first";
        }else if(num_set == 2){
            num = "second";
        }else if(num_set == 3){
            num = "third";
        }
        Toast.makeText(this, player + " won the " + num + " set!", Toast.LENGTH_SHORT).show();
    }

    //determines if there's already a winner for this tournament
    //if a player won 2 sets, the function returns true
    private Boolean winnerExists() {
        int wins1 = 0;
        int wins2 = 0;

        for(int i = 0; i < 3; i++) {
            if(score1[i] > score2[i]) {
                wins1++;
            } else if (score2[i] > score1[i]){
                wins2++;
            }
        }
        if(wins1 >= 2 || wins2 >= 2) {
            return true;
        }
        return false;
    }

    //function to show a dialogue when a user presses the button to end the game
    //if the player chooses to end, goes back to main activity
    private void endGame() {
        AlertDialog.Builder alertEnd = new AlertDialog.Builder(this, R.style.CustomMaterialDialog);
        alertEnd.setMessage("Ending the game will reset everything and go back to main page. Proceed?");
        alertEnd.setTitle("End Game");
        alertEnd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                api.deleteGame(token, game.getId(), new ClientDAO.deleteGameListener() {
                    @Override
                    public void onSuccess(String message) {
                        //apagou o jogo
                    }

                    @Override
                    public void onError(String message) {
                        //nao apagou...
                    }
                });
                finish();
            }
        });
        alertEnd.setNegativeButton("Cancel", null);
        alertEnd.show();
    }


    //function for menu items and return button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.games_button:
                Intent iViewGames = new Intent(this, ViewGamesActivity.class);
                startActivity(iViewGames);
                return true;
            case R.id.edit_button:
                Intent iEditGame = new Intent(this, EditActivity.class);
                iEditGame.putExtra("player1", name_player1.getText().toString());
                iEditGame.putExtra("player2", name_player2.getText().toString());
                iEditGame.putExtra("tournament", tournament.getText().toString());
                startActivityForResult(iEditGame, 1);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            Bundle bundle = data.getExtras();
            tournament.setText(bundle.getString("tournament"));
            name_player1.setText(bundle.getString("player1"));
            name_player2.setText(bundle.getString("player2"));
            player1.setText(bundle.getString("player1"));
            player2.setText(bundle.getString("player2"));
        }
    }


    //function to save application state
    public void onSaveInstanceState(Bundle outState){
        outState.putCharSequence("tournament", tournament.getText());
        outState.putCharSequence("player1", player1.getText());
        outState.putCharSequence("player2", player2.getText());
        outState.putIntArray("score1", score1);
        outState.putIntArray("score2", score2);
        outState.putCharSequence("pt_player1", pt_player1.getText());
        outState.putCharSequence("pt_player2",pt_player2.getText());
        outState.putInt("num_set", num_set);
        super.onSaveInstanceState(outState);
    }

    //function to restore application state
    public void onRestoreInstanceState(Bundle outState){
        super.onRestoreInstanceState(outState);

        tournament.setText(outState.getCharSequence("tournament"));
        player1.setText(outState.getCharSequence("player1"));
        name_player1.setText(outState.getCharSequence("player1"));
        player2.setText(outState.getCharSequence("player2"));
        name_player2.setText(outState.getCharSequence("player2"));

        score1 = outState.getIntArray("score1");
        score2 = outState.getIntArray("score2");

        set1_p1.setText(score1[0] + "");
        set2_p1.setText(score1[1] + "");
        set3_p1.setText(score1[2] + "");
        set1_p2.setText(score2[0] + "");
        set2_p2.setText(score2[1] + "");
        set3_p2.setText(score2[2] + "");

        pt_player1.setText(outState.getCharSequence("pt_player1"));
        pt_player2.setText(outState.getCharSequence("pt_player2"));

        num_set = outState.getInt("num_set");
        setNum.setText("Set " + num_set);
    }
}