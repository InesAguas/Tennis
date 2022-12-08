package estgoh.tam.taniaines.tennis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class GameActivity extends AppCompatActivity{

    private TextView tournament, player1, player2, pt_player1, pt_player2, set1_p1,set1_p2,set2_p1,set2_p2,set3_p1,set3_p2,name_player1, name_player2;
    private Button btn_player1, btn_player2, btn_end;
    private int num_set;
    private TextView setNum;
    private int[] score1 = {0, 0, 0};
    private int[] score2 = {0, 0, 0};

    GameDBAdapter gAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Live Game");

        gAdapter = new GameDBAdapter(this);


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
                deleteDialog();
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

    //function to change score numbers when player 2 scores
    private void player2Scores() {
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

    //function to change set scores when a player wins a game
    private void changeScore(String player) {
        pt_player1.setText("0");
        pt_player2.setText("0");

        if(player.equals("p1")) {
            score1[num_set-1] += 1;
            set1_p1.setText(score1[0] + "");
            set2_p1.setText(score1[1] + "");
            set3_p1.setText(score1[2] + "");
            if(score1[num_set-1] >= 6 && (score2[num_set-1] <= score1[num_set-1]-2)) {
                congratsMessage_set(player1.getText());
                if(!winnerExists()) {
                    num_set++;
                    setNum.setText("Set " + num_set);
                } else {
                    saveGame();
                    resetScores();
                }
            }
        } else {
            score2[num_set-1] += 1;
            set1_p2.setText(score2[0] + "");
            set2_p2.setText(score2[1] + "");
            set3_p2.setText(score2[2] + "");
            if(score2[num_set-1] >= 6 && (score1[num_set-1] <= score2[num_set-1]-2)) {
                congratsMessage_set(player2.getText());
                if(!winnerExists()) {
                    num_set++;
                    setNum.setText("Set " + num_set);
                } else {
                    saveGame();
                    resetScores();
                }
            }
        }
    }

    //function to save the game
    private void saveGame(){
        int[] sc1 = new int[3];
        int[] sc2 = new int[3];
        for(int i = 0; i < 3; i++) {
            sc1[i] = score1[i];
            sc2[i] = score2[i];
        }

        gAdapter.open();
        gAdapter.insertGame(tournament.getText().toString(), player1.getText().toString(), player2.getText().toString(), sc1, sc2);
        gAdapter.close();

        Game game = new Game(tournament.getText().toString(), player1.getText().toString(), player2.getText().toString(), sc1, sc2, new Date());
        AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this, R.style.CustomMaterialDialog);
        alert.setTitle("Game saved");
        if(game.getWinner() == 1) {
            alert.setMessage("Congratulations, " + game.getPlayer1() + " you won the game. The game was automatically saved.");
        } else {
            alert.setMessage("Congratulations, " + game.getPlayer2() + " you won the game. The game was automatically saved.");
        }
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    //function to reset all scores
    private void resetScores() {
        for(int i = 0; i < 3; i++) {
            score1[i] = 0;
            score2[i] = 0;
        }

        num_set = 1;
        set1_p1.setText(score1[0] + "");
        set2_p1.setText(score1[1] + "");
        set3_p1.setText(score1[2] + "");
        set1_p2.setText(score2[0] + "");
        set2_p2.setText(score2[1] + "");
        set3_p2.setText(score2[2] + "");
        setNum.setText("Set " + num_set);
        pt_player1.setText("0");
        pt_player2.setText("0");
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
    //if the player chooses to delete, restarts scores
    private void deleteDialog() {
        AlertDialog.Builder alertDelete = new AlertDialog.Builder(this, R.style.CustomMaterialDialog);
        alertDelete.setMessage("Deleting will restart all score results for the game and no data will be saved.");
        alertDelete.setTitle("Delete?");
        alertDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetScores();
            }
        });
        alertDelete.setNegativeButton("Cancel", null);
        alertDelete.show();
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

    //ja guarda todos os resultados ao trocar a orientação do ecra
    //falta guardar os dados que sao alterados quando se edita os nomes e etc..
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