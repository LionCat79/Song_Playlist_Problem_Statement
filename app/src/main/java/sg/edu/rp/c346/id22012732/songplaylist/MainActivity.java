package sg.edu.rp.c346.id22012732.songplaylist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText songTitleEditText;
    EditText singersEditText;
    EditText releaseYearText;
    RadioGroup ratings;
    Button insertButton;
    Button displayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songTitleEditText = findViewById(R.id.editTextTitle);
        singersEditText = findViewById(R.id.editTextSingers);
        releaseYearText = findViewById(R.id.editTextYear);
        ratings = findViewById(R.id.rating);
        insertButton = findViewById(R.id.btnUpdate);
        displayButton = findViewById(R.id.btnDelete);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = ratings.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedNumber = selectedRadioButton.getText().toString();
                // Get the entered song title and artist
                String songTitle = songTitleEditText.getText().toString();
                String singers = singersEditText.getText().toString();
                int year = Integer.parseInt(releaseYearText.getText().toString());
                int stars = Integer.parseInt(selectedNumber);

                // Insert the song record into the database using your preferred method or framework
                insertSongRecord(songTitle, singers, year, stars);

                // Clear the input fields after insertion
                songTitleEditText.setText("");
                singersEditText.setText("");
                releaseYearText.setText("");
            }
        });

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the DisplayActivity to show the records
                Intent intent = new Intent(MainActivity.this, SongListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertSongRecord(String songTitle, String singers, int year, int stars) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, songTitle);
        values.put(DBHelper.COLUMN_SINGERS, singers);
        values.put(DBHelper.COLUMN_YEAR, year);
        values.put(DBHelper.COLUMN_STARS, stars);

        long result = db.insert(DBHelper.TABLE_SONG, null, values);
        db.close();
    }
}