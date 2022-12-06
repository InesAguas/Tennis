package estgoh.tam.taniaines.tennis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class GameAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Game> adaptGames;

    public GameAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.adaptGames = games;
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
        set1P1.setText(game.getScore1(0) + "");
        set2P1.setText(game.getScore1(1) + "");
        set3P1.setText(game.getScore1(2) + "");
        set1P2.setText(game.getScore2(0) + "");
        set2P2.setText(game.getScore2(1) + "");
        set3P2.setText(game.getScore2(2) + "");

        if(game.getScore1(0) > game.getScore2(0)) {
            set1P1.setTypeface(null, Typeface.BOLD);
            set1P1.setTextColor(Color.rgb(0,153,0));
            set1P2.setTypeface(null, Typeface.NORMAL);
            set1P2.setTextColor(Color.rgb(60,60,60));
        } else {
            set1P2.setTypeface(null, Typeface.BOLD);
            set1P2.setTextColor(Color.rgb(0,153,0));
            set1P1.setTypeface(null, Typeface.NORMAL);
            set1P1.setTextColor(Color.rgb(60,60,60));
        }

        if(game.getScore1(1) > game.getScore2(1)) {
            set2P1.setTypeface(null, Typeface.BOLD);
            set2P1.setTextColor(Color.rgb(0,153,0));
            set2P2.setTypeface(null, Typeface.NORMAL);
            set2P2.setTextColor(Color.rgb(60,60,60));
        } else {
            set2P2.setTypeface(null, Typeface.BOLD);
            set2P2.setTextColor(Color.rgb(0,153,0));
            set2P1.setTypeface(null, Typeface.NORMAL);
            set2P1.setTextColor(Color.rgb(60,60,60));
        }

        if(game.getScore1(2) != 0 || game.getScore2(2) != 0) {
            if(game.getScore1(2) > game.getScore2(2)) {
                set3P1.setTypeface(null, Typeface.BOLD);
                set3P1.setTextColor(Color.rgb(0,153,0));
                set3P2.setTypeface(null, Typeface.NORMAL);
                set3P2.setTextColor(Color.rgb(60,60,60));
            } else {
                set3P2.setTypeface(null, Typeface.BOLD);
                set3P2.setTextColor(Color.rgb(0,153,0));
                set3P1.setTypeface(null, Typeface.NORMAL);
                set3P1.setTextColor(Color.rgb(60,60,60));
            }
        } else {
            set3P2.setTypeface(null, Typeface.NORMAL);
            set3P2.setTextColor(Color.rgb(60,60,60));
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

        return view;
    }

    private void deleteGame(View view, int position) {
        AlertDialog.Builder deleteGame = new AlertDialog.Builder(view.getContext(), R.style.CustomMaterialDialog);
        deleteGame.setTitle("Delete game");
        deleteGame.setMessage("Are you sure you want to delete this game?");
        deleteGame.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.games.remove(position);
                notifyDataSetChanged();
            }
        });
        deleteGame.setNegativeButton("No", null);
        deleteGame.show();
    }
}