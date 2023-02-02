package estgoh.tam.taniaines.tennis.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//class Game implements Comparable so we can order items by date
public class Game implements Comparable<Game>, Serializable {

    private int id, stage, winner;
    private String player1, player2, tournament, points1, points2;
    private int[] score1;
    private int[] score2;
    private Date date;

    //game constructor
    public Game(String tournament, String player1, String player2, int[] score1, int[] score2, Date date) {
        this.tournament = tournament;
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = score1;
        this.score2 = score2;
        this.date = date;
        this.winner = setWinner();
        this.stage = 1;
        this.points1 = "0";
        this.points2 = "0";
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

    public int getSetScore1(int position) {
        return score1[position];
    }

    public int getSetScore2(int position) {
        return score2[position];
    }

    public int[] getScore1() {
        return score1;
    }

    public int[] getScore2() {
        return score2;
    }

    public Date getDate() {
        return date;
    }

    //function to get the date as a string already formatted
    public String getDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public int getStage() {
        return stage;
    }

    public int getWinner() {
        return winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setScore1(int[] score1) {
        this.score1 = score1;
    }

    public void setScore2(int[] score2) {
        this.score2 = score2;
    }

    public String getPoints1() {
        return points1;
    }

    public String getPoints2() {
        return points2;
    }

    public void setPoints1(String points1) {
        this.points1 = points1;
    }

    public void setPoints2(String points2) {
        this.points2 = points2;
    }

    public int currentSet() {
        //7-6 ganha, 7-5 ganha, 6-4,6-3,etc ganha
        for(int i = 0; i < 3; i++) {
            if(!(score1[i] > 6 || score1[i] == 6 && score2[i] <= 4) && !(score2[i] > 6 || score2[i] == 6 && score1[i] <= 4))
                return i+1;
        }
        return 0;
    }
    //function to compare games by date
    @Override
    public int compareTo(Game game) {
        return game.getDate().compareTo(date);
    }
}
