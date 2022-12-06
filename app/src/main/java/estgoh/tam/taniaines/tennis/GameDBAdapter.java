package estgoh.tam.taniaines.tennis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDBAdapter {

    String DB_NAME = "TennisDatabase";
    String DB_TABLE = "games";
    int DB_VERSION = 1;

    DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    String SQL_CREATE = "CREATE TABLE " + DB_TABLE +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tournament TEXT NOT NULL, " +
            "player1 TEXT NOT NULL, " +
            "player2 TEXT NOT NULL, " +
            "score11 INTEGER, score12 INTEGER, score13 INTEGER, " +
            "score21 INTEGER, score22 INTEGER, score23 INTEGER, " +
            "date DATE)";

    String SQL_DROP = "DROP TABLE IF EXISTS " + DB_TABLE;

    public GameDBAdapter(Context context){

        dbHelper = new DatabaseHelper(context);
    }

    class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DROP);
            onCreate(db);
        }

    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public long insertGame(String tournament, String player1, String player2, int[] score1, int[] score2) {
        String sql = "INSERT INTO " + DB_TABLE + " (tournament, player1, player2, score11, score12, score13, score21, score22," +
                "score23, date) VALUES (?, ?, ?,?, ?, ?,?, ?, ?, date());";
        Object[] args = new Object[]{tournament, player1, player2, score1[0], score1[1], score1[2], score2[0], score2[1], score2[2]};

        try{
            db.execSQL(sql, args);
            return 1;
        }
        catch(SQLException e){
            return -1;
        }
    }

    public Cursor getAllGames() {
        String sql = "SELECT * FROM " + DB_TABLE + "  ORDER BY date DESC;";
        return db.rawQuery(sql, null);
    }

    public int deleteGame(int id){
        String sql = "DELETE FROM " + DB_TABLE + " WHERE id=?;";
        Object[] args = new Object[]{id};
        try{
            db.execSQL(sql, args);
            return 1;
        }
        catch(SQLException e){
            return -1;
        }
    }
}
