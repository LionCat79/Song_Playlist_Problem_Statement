package sg.edu.rp.c346.id22012732.songplaylist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import sg.edu.rp.c346.id22012732.songplaylist.Song;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "song.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_SONG = "song";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SINGERS = "singers";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_SONG + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " INTEGER)";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }


    public long insertSong(String title, String singers, int year, int stars) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONG, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
            int singersIndex = cursor.getColumnIndex(COLUMN_SINGERS);
            int yearIndex = cursor.getColumnIndex(COLUMN_YEAR);
            int starsIndex = cursor.getColumnIndex(COLUMN_STARS);

            do {
                String title = cursor.getString(titleIndex);
                String singers = cursor.getString(singersIndex);
                int year = cursor.getInt(yearIndex);
                int stars = cursor.getInt(starsIndex);
                Song song = new Song(title, singers, year, stars);
                songList.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songList;
    }
}