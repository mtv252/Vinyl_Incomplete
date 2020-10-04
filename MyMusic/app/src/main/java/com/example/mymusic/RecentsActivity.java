package com.example.mymusic;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = this.getResources();

        TextView headerText = (TextView) findViewById(R.id.header_text);
        headerText.setText("Recently Played");

        final Album selection;
        final ArrayList<Album> recentlyPlayed = new ArrayList<Album>();
        //Receives info on the previous albums selected by the user.
        if (getIntent().getExtras() != null) {
            ArrayList recentList = (ArrayList) getIntent().getSerializableExtra("recentList");
            recentlyPlayed.addAll(recentList);

            //Applies an ArrayAdapter to the list of previously selected albums.
            AlbumAdapter libraryAdapter = new AlbumAdapter(this, recentlyPlayed);
            final GridView gridView = (GridView) findViewById(R.id.album_library);
            gridView.setAdapter(libraryAdapter);
            // Sets a click listener to each item of thd grid that will bring user to NowPlaying.
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(RecentsActivity.this, NowPlayingActivity.class);
                    Album instance = (Album) gridView.getItemAtPosition(i);
                    //Checks if an album is already on the list before adding it on.
                    recentlyPlayed.remove(i);
                    recentlyPlayed.add(0, instance);
                    intent.putExtra("Album", instance);
                    intent.putExtra("RecentList", recentlyPlayed);
                    startActivity(intent);
                }
            });
        }

        //Sets up the Bottom Navigation Bar
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_main_activity);
        bottomNavigationView.setSelectedItemId(R.id.nav_recently_played);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_now_playing:
                                Intent intent1 = new Intent(RecentsActivity.this, NowPlayingActivity.class);
                                if (getIntent().getExtras() != null) {
                                    Album selection = (Album) getIntent().getSerializableExtra("Album");
                                    intent1.putExtra("Album", selection);
                                    intent1.putExtra("recentList", recentlyPlayed);
                                }
                                startActivity(intent1);
                                return true;
                            case R.id.nav_library:
                                Intent intent2 = new Intent(RecentsActivity.this, LibraryActivity.class);
                                if (getIntent().getExtras() != null) {
                                    Album selection = (Album) getIntent().getSerializableExtra("Album");
                                    intent2.putExtra("Album", selection);
                                    intent2.putExtra("recentList", recentlyPlayed);
                                }
                                startActivity(intent2);
                                return true;
                        }
                        return false;
                    }
                });
    }
}
