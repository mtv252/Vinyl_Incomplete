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

import java.util.ArrayList;



public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        ArrayList<Album> albums = new ArrayList<>();
        albums.add(new Album("Owls", "Owls", R.drawable.owls));
        albums.add(new Album("Xenia Rubinos", "Black Terry Cat", R.drawable.black_terry_cat));
        albums.add(new Album("Palm", "Rock Island", R.drawable.rock_island));
        albums.add(new Album("J Dilla", "Donuts", R.drawable.donuts));
        albums.add(new Album("Owls", "Owls", R.drawable.owls));
        albums.add(new Album("Xenia Rubinos", "Black Terry Cat", R.drawable.black_terry_cat));

        final ArrayList<Album> recentlyPlayed = new ArrayList<Album>();
        if (getIntent().getExtras() != null) {
            ArrayList recentList = (ArrayList) getIntent().getSerializableExtra("recentList");
            recentlyPlayed.addAll(recentList);
        }

        AlbumAdapter libraryAdapter = new AlbumAdapter(this, albums);

        final GridView gridView = (GridView) findViewById(R.id.album_library);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LibraryActivity.this, NowPlayingActivity.class);
                Album instance = (Album) gridView.getItemAtPosition(i);
                if (recentlyPlayed != null) {
                    for (Album j : recentlyPlayed) {
                        if (j.getAlbumName().equals(instance.getAlbumName())) {
                            recentlyPlayed.remove(j);
                            break;
                        }
                    }
                }
                recentlyPlayed.add(0, instance);
                intent.putExtra("Album", instance);
                intent.putExtra("recentList", recentlyPlayed);
                startActivity(intent);
            }
        });
        gridView.setAdapter(libraryAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_main_activity);
        bottomNavigationView.setSelectedItemId(R.id.nav_library);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_now_playing:
                                Intent intent1 = new Intent(LibraryActivity.this, NowPlayingActivity.class);
                                if(getIntent().getExtras() != null) {
                                    Album selection = (Album) getIntent().getSerializableExtra("Album");
                                    ArrayList recentList = (ArrayList) getIntent().getSerializableExtra("recentList");
                                    intent1.putExtra("Album", selection);
                                    intent1.putExtra("recentList", recentList);
                                }
                                startActivity(intent1);
                                return true;
                            case R.id.nav_recently_played:
                                Intent intent2 = new Intent(LibraryActivity.this, RecentsActivity.class);
                                if(getIntent().getExtras() != null) {
                                    Album selection = (Album) getIntent().getSerializableExtra("Album");
                                    ArrayList recentList = (ArrayList) getIntent().getSerializableExtra("recentList");
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