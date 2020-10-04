package com.example.mymusic;

import java.io.Serializable;


public class Album implements Serializable {

    private String mArtistName;
    private String mAlbumName;
    private int mAlbumArt;

    public Album(String artistName, String albumName, int albumArt) {
        mArtistName = artistName;
        mAlbumName = albumName;
        mAlbumArt = albumArt;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public int getAlbumArt() {
        return mAlbumArt;
    }
}
