package estgoh.tam.taniaines.tennis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//class Game implements Comparable so we can order items by date
public class Game implements Comparable<Game> {

    private String player1, player2, tournament;
    private int[] score1;
    private int[] score2;
    private Date date;
    private int winner, id;

    //game constructor
    Game(String tournament, String player1, String player2, int[] score1, int[] score2, Date date) {
        this.tournament = tournament;
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = score1;
        this.score2 = score2;
        this.date = date;
        this.winner = setWinner();
    }

    Game(int id, String tournament, String player1, String player2, int[] score1, int[] score2, Date date) {
        this(tournament, player1, player2, score1, score2, date);
        this.id = id;
    }

    //function to determine the winner of the game
    private int setWinner() {
        if(countWins(1) > countWins(2)) {
            return 1;
        } else {
            return 2;
        }
    }

    //function to count player wins
    private int countWins(int player) {
        int wins = 0;
        if(player == 1) {
            for(int i = 0; i < 3; i++) {
                if(score1[i] > score2[i]) {
                    wins++;
                }
            }
        } else {
            for(int i = 0; i < 3; i++) {
                if(score2[i] > score1[i]) {
                    wins++;
                }
            }
        }
        return wins;
    }

    public String getTournament() {
        return tournament;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getScore1(int position) {
        return score1[position];
    }

    public int getScore2(int position) {
        return score2[position];
    }

    public Date getDate() {
        return date;
    }

    //function to get the date as a string already formatted
    public String getDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public int getWinner() {
        return winner;
    }

    public int getId() {
        return id;
    }
    //function to compare games by date
    @Override
    public int compareTo(Game game) {
        return game.getDate().compareTo(date);
    }
}
