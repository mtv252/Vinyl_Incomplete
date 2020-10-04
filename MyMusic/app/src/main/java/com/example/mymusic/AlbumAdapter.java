package com.example.mymusic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumAdapter extends ArrayAdapter<Album> {

    public AlbumAdapter(Activity context, ArrayList<Album> numbers) {
        super(context, 0, numbers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }



        // Get the {@link AndroidFlavor} object located at this position in the list
        Album currentAlbum = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        ImageView albumArtView = (ImageView) gridItemView.findViewById(R.id.album_art);
        albumArtView.setImageResource(currentAlbum.getAlbumArt());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView artistTextView = (TextView) gridItemView.findViewById(R.id.artist_name);
        artistTextView.setText(currentAlbum.getArtistName());
        artistTextView.setTextColor(Color.BLACK);
        artistTextView.setTextSize(18);

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView albumTextView = (TextView) gridItemView.findViewById(R.id.album_name);
        albumTextView.setText(currentAlbum.getAlbumName());
        albumTextView.setTypeface(null, Typeface.ITALIC);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return gridItemView;

    }
}


