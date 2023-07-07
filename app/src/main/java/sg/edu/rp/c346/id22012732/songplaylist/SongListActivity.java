package sg.edu.rp.c346.id22012732.songplaylist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Song> songList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        listView = findViewById(R.id.listView);
        songList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        // Retrieve the songs from the database
        songList = dbHelper.getAllSongs();

        // Create an ArrayList of strings to store the song titles
        ArrayList<String> songTitles = new ArrayList<>();
        for (Song song : songList) {
            songTitles.add(song.getTitle());
        }

        // Create the ArrayAdapter to adapt the song titles to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songTitles);

        // Set the ArrayAdapter as the adapter for the ListView
        listView.setAdapter(adapter);
    }
}