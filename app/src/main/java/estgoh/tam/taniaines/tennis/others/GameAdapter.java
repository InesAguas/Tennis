package estgoh.tam.taniaines.tennis.others;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estgoh.tam.taniaines.tennis.R;
import estgoh.tam.taniaines.tennis.classes.Game;
import estgoh.tam.taniaines.tennis.classes.User;

public class GameAdapter extends BaseAdapter {

    private Context context;
    private List<Game> adaptGames;
    private String token;
    ClientDAO api;

    public GameAdapter(Context context, List<Game> games, String token) {
        this.context = context;
        this.adaptGames = games;
        this.token = token;
    }

    @Override
    public int getCount() {
        return adaptGames.size();
    }

    @Override
    public Object getItem(int i) {
        return adaptGames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view, viewGroup, false);
        }

        TextView nameTour = view.findViewById(R.id.listNameTour);
        TextView date = view.findViewById(R.id.listDate);
        TextView player1 = view.findViewById(R.id.listPlayer1);
        TextView player2 = view.findViewById(R.id.listPlayer2);
        TextView set1P1 = view.findViewById(R.id.listSet1P1);
        TextView set2P1 = view.findViewById(R.id.listSet2P1);
        TextView set3P1 = view.findViewById(R.id.listSet3P1);
        TextView set1P2 = view.findViewById(R.id.listSet1P2);
        TextView set2P2 = view.findViewById(R.id.listSet2P2);
        TextView set3P2 = view.findViewById(R.id.listSet3P2);
        ImageView liveImage = view.findViewById(R.id.liveImage);

        ImageButton deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGame(view, i);
            }
        });

        Game game = adaptGames.get(i);

        nameTour.setText(game.getTournament());
        date.setText(game.getDateFormatted());
        player1.setText(game.getPlayer1());
        player2.setText(game.getPlayer2());
        set1P1.setText(game.getSetScore1(0) + "");
        set2P1.setText(game.getSetScore1(1) + "");
        set3P1.setText(game.getSetScore1(2) + "");
        set1P2.setText(game.getSetScore2(0) + "");
        set2P2.setText(game.getSetScore2(1) + "");
        set3P2.setText(game.getSetScore2(2) + "");

        if(game.getStage() != 0) {
            liveImage.setVisibility(View.VISIBLE);
            if(game.getSetScore1(0) > 6 || game.getSetScore1(0) == 6 && game.getSetScore2(0) <= 4) {
                set1P1.setTypeface(null, Typeface.BOLD);
                set1P1.setTextColor(Color.rgb(0,153,0));
            }else if(game.getSetScore2(0) > 6 || game.getSetScore2(0) == 6 && game.getSetScore1(0) <= 4) {
                set1P2.setTypeface(null, Typeface.BOLD);
                set1P2.setTextColor(Color.rgb(0,153,0));
            }
            if(game.getSetScore1(1) > 6 || game.getSetScore1(1) == 6 && game.getSetScore2(1) <= 4) {
                set2P1.setTypeface(null, Typeface.BOLD);
                set2P1.setTextColor(Color.rgb(0,153,0));
            }else if(game.getSetScore2(1) > 6 || game.getSetScore2(1) == 6 && game.getSetScore1(1) <= 4) {
                set2P2.setTypeface(null, Typeface.BOLD);
                set2P2.setTextColor(Color.rgb(0,153,0));
            }
            if(game.getSetScore1(2) > 6 || game.getSetScore1(2) == 6 && game.getSetScore2(2) <= 4) {
                set3P1.setTypeface(null, Typeface.BOLD);
                set3P1.setTextColor(Color.rgb(0,153,0));
            }else if(game.getSetScore2(2) > 6 || game.getSetScore2(2) == 6 && game.getSetScore1(2) <= 4) {
                set3P2.setTypeface(null, Typeface.BOLD);
                set3P2.setTextColor(Color.rgb(0,153,0));
            }
        } else {
            liveImage.setVisibility(View.INVISIBLE);
            if(game.getSetScore1(0) > game.getSetScore2(0)) {
                set1P1.setTypeface(null, Typeface.BOLD);
                set1P1.setTextColor(Color.rgb(0,153,0));
                set1P2.setTypeface(null, Typeface.NORMAL);
                //set1P2.setTextColor(Color.rgb(60,60,60));
            } else {
                set1P2.setTypeface(null, Typeface.BOLD);
                set1P2.setTextColor(Color.rgb(0,153,0));
                set1P1.setTypeface(null, Typeface.NORMAL);
                //set1P1.setTextColor(Color.rgb(60,60,60));
            }

            if(game.getSetScore1(1) > game.getSetScore2(1)) {
                set2P1.setTypeface(null, Typeface.BOLD);
                set2P1.setTextColor(Color.rgb(0,153,0));
                set2P2.setTypeface(null, Typeface.NORMAL);
                //set2P2.setTextColor(Color.rgb(60,60,60));
            } else {
                set2P2.setTypeface(null, Typeface.BOLD);
                set2P2.setTextColor(Color.rgb(0,153,0));
                set2P1.setTypeface(null, Typeface.NORMAL);
                //set2P1.setTextColor(Color.rgb(60,60,60));
            }

            if(game.getSetScore1(2) != 0 || game.getSetScore2(2) != 0) {
                if(game.getSetScore1(2) > game.getSetScore2(2)) {
                    set3P1.setTypeface(null, Typeface.BOLD);
                    set3P1.setTextColor(Color.rgb(0,153,0));
                    set3P2.setTypeface(null, Typeface.NORMAL);
                    //set3P2.setTextColor(Color.rgb(60,60,60));
                } else {
                    set3P2.setTypeface(null, Typeface.BOLD);
                    set3P2.setTextColor(Color.rgb(0,153,0));
                    set3P1.setTypeface(null, Typeface.NORMAL);
                   // set3P1.setTextColor(Color.rgb(60,60,60));
                }
            } else {
                set3P2.setText("-");
                set3P2.setTypeface(null, Typeface.NORMAL);
                set3P2.setTextColor(Color.rgb(60,60,60));
                set3P1.setText("-");
                set3P1.setTypeface(null, Typeface.NORMAL);
                set3P1.setTextColor(Color.rgb(60,60,60));
            }

            if(game.getWinner() == 1) {
                view.findViewById(R.id.winner1Image).setVisibility(View.VISIBLE);
                view.findViewById(R.id.winner2Image).setVisibility(View.INVISIBLE);
            } else {
                view.findViewById(R.id.winner2Image).setVisibility(View.VISIBLE);
                view.findViewById(R.id.winner1Image).setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }

    private void deleteGame(View view, int position) {
        AlertDialog.Builder deleteGameDialog = new AlertDialog.Builder(context, R.style.CustomMaterialDialog);

        deleteGameDialog.setTitle("Delete game");
        deleteGameDialog.setMessage("Are you sure you want to delete this game?");
        deleteGameDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                api = new RESTClientDAO(context);
                Game game = adaptGames.get(position);
                api.deleteGame(token, game.getId(), new ClientDAO.deleteGameListener() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                        adaptGames.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        deleteGameDialog.setNegativeButton("No", null);
        deleteGameDialog.show();
    }
}