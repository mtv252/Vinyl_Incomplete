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

        //Creates the library of albums the user can select.

        ArrayList<Album> albums = new ArrayList<>();
        albums.add(new Album("Owls", "Owls", R.drawable.owls));
        albums.add(new Album("Xenia Rubinos", "Black Terry Cat", R.drawable.black_terry_cat));
        albums.add(new Album("Palm", "Rock Island", R.drawable.rock_island));
        albums.add(new Album("J Dilla", "Donuts", R.drawable.donuts));
        albums.add(new Album("Owls", "Owls", R.drawable.owls));
        albums.add(new Album("Xenia Rubinos", "Black Terry Cat", R.drawable.black_terry_cat));

        /**
         * instantiates a list to store albums the user has played, and checks to see if
         * there's any info from previous activities
         */

        final ArrayList<Album> recentlyPlayed = new ArrayList<Album>();
        if (getIntent().getExtras() != null) {
            ArrayList recentList = (ArrayList) getIntent().getSerializableExtra("recentList");
            recentlyPlayed.addAll(recentList);
        }


        // Creates a gridAdapter applies it to the library of albums.

        AlbumAdapter libraryAdapter = new AlbumAdapter(this, albums);

        final GridView gridView = (GridView) findViewById(R.id.album_library);
       // Sets a click listener to each item of thd grid that will bring user to NowPlaying.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LibraryActivity.this, NowPlayingActivity.class);
                Album instance = (Album) gridView.getItemAtPosition(i);
                // Checks to see if the album is already on the Recents list before adding it to the top of the list
                if (recentlyPlayed != null) {
                    for (Album j : recentlyPlayed) {
                        if (j.getAlbumName().equals(instance.getAlbumName())) {
                            recentlyPlayed.remove(j);
                            break;
                        }
                    }
                }
                recentlyPlayed.add(0, instance);
                //Stores data on the selected album to pass to the NowPlaying activity
                intent.putExtra("Album", instance);
                intent.putExtra("recentList", recentlyPlayed);
                startActivity(intent);
            }
        });
        gridView.setAdapter(libraryAdapter);

        //Rigs the Bottom Navigation Bar up to move between activites, and bring relevant info along.

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