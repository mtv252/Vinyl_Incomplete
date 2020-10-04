package com.example.mymusic;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NowPlayingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_activity);
        Resources res = this.getResources();

        RelativeLayout wholeView = (RelativeLayout) findViewById(R.id.now_playing_view);

        //Receives info on the album selected by the user.
        final ArrayList<Album> recentlyPlayed = new ArrayList<Album>();
        if (getIntent().getExtras() != null) {
            Album selection = (Album) getIntent().getSerializableExtra("Album");
            ArrayList<Album> recentList = (ArrayList<Album>) getIntent().getSerializableExtra("recentList");
            recentlyPlayed.addAll(recentList);

            //Applies the data from the chosen album onto the Layout
            ImageView albumArt = (ImageView) findViewById(R.id.now_playing_art);
            albumArt.setImageResource(selection.getAlbumArt());

            TextView artistName = (TextView) findViewById(R.id.now_playing_artist);
            artistName.setText(selection.getArtistName() + " - " + selection.getAlbumName());

            //Creates an ImageView that toggles between Play and Pause when touched.
            final ImageView transportButton = (ImageView) findViewById(R.id.play_button);
            final Drawable playButton = ResourcesCompat.getDrawable(res, R.drawable.playbuttontake8, null);
            final Drawable pauseButton = ResourcesCompat.getDrawable(res, R.drawable.pause_button, null);
            transportButton.setImageDrawable(playButton);
            transportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (transportButton.getDrawable() == playButton) {
                        transportButton.setImageDrawable(pauseButton);
                    } else {
                        transportButton.setImageDrawable(playButton);
                    }
                }
            });
        }

        //Rigs the Bottom Navigation Bar up to move between activites, and bring relevant info along.
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_bar_now_playing);
        bottomNavigationView.setSelectedItemId(R.id.nav_now_playing);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_library:
                                Intent intent1 = new Intent(NowPlayingActivity.this, LibraryActivity.class);
                                if (getIntent().getExtras() != null) {
                                    Album selection = (Album) getIntent().getSerializableExtra("Album");
                                    intent1.putExtra("Album", selection);
                                    intent1.putExtra("recentList", recentlyPlayed);
                                }
                                startActivity(intent1);
                                return true;
                            case R.id.nav_recently_played:
                                Intent intent2 = new Intent(NowPlayingActivity.this, RecentsActivity.class);
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