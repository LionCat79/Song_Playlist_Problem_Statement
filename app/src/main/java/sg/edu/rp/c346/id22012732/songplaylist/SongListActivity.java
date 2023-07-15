package sg.edu.rp.c346.id22012732.songplaylist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    private ListView listView;
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

        // Create the ArrayList of song information strings
        ArrayList<String> songInfoList = new ArrayList<>();
        for (Song song : songList) {
            String songInfo = "Title: " + song.getTitle() + "\n"
                    + "Singer: " + song.getSingers() + "\n"
                    + "Year: " + song.getYear() + "\n"
                    + "Rating: " + getStarRating(song.getStars());
            songInfoList.add(songInfo);
        }

        // Create the ArrayAdapter to adapt the song information to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, songInfoList);

        // Set the ArrayAdapter as the adapter for the ListView
        listView.setAdapter(adapter);

        // Set the OnItemClickListener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked song
                Song clickedSong = songList.get(position);

                // Pass the clicked song properties as extras in the Intent
                Intent intent = new Intent(SongListActivity.this, SongDetailsActivity.class);
                intent.putExtra("title", clickedSong.getTitle());
                intent.putExtra("singers", clickedSong.getSingers());
                intent.putExtra("year", clickedSong.getYear());
                intent.putExtra("stars", clickedSong.getStars());
                startActivity(intent);
            }
        });
    }

    private String getStarRating(int stars) {
        String starRating = "";
        for (int i = 0; i < stars; i++) {
            starRating += "*";
        }
        return starRating;
    }
}