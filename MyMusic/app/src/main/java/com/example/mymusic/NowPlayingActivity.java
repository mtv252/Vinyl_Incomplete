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

        if (getIntent().getExtras() != null) {
            Album selection = (Album) getIntent().getSerializableExtra("Album");
            ArrayList<Album> recentList = (ArrayList<Album>) getIntent().getSerializableExtra("recentList");


            ImageView albumArt = (ImageView) findViewById(R.id.now_playing_art);
            albumArt.setImageResource(selection.getAlbumArt());

            TextView artistName = (TextView) findViewById(R.id.now_playing_artist);
            artistName.setText(selection.getArtistName() + " - " + selection.getAlbumName());

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
                                    ArrayList<Album> recentList = (ArrayList<Album>) getIntent().getSerializableExtra("recentList");
                                    intent1.putExtra("Album", selection);
                                    intent1.putExtra("recentList", recentList);
                                }
                                startActivity(intent1);
                                return true;
                            case R.id.nav_recently_played:
                                Intent intent2 = new Intent(NowPlayingActivity.this, RecentsActivity.class);
                                if (getIntent().getExtras() != null) {
                                    Album selection = (Album) getIntent().getSerializableExtra("Album");
                                    ArrayList<Album> recentList = (ArrayList<Album>) getIntent().getSerializableExtra("recentList");
                                    intent2.putExtra("Album", selection);
                                    intent2.putExtra("recentList", recentList);
                                }
                                startActivity(intent2);
                                return true;
                        }
                        return false;
                    }
                });
    }
}