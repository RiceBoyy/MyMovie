package com.example.mymovie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static List<MovieItem> watchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Button addButton = findViewById(R.id.add_watchlist_button);
        addButton.setOnClickListener(view -> {
            String title = getIntent().getStringExtra("title");
            String imageUrl = getIntent().getStringExtra("imageUrl");
            String description = getIntent().getStringExtra("description");

            // Add the movie to the watchlist
            watchList.add(new MovieItem(title, imageUrl, description));

            // Feedback for the user
            Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show();
        });

        Button removeButton = findViewById(R.id.button_remove);
        removeButton.setOnClickListener(view -> {
            String titleToRemove = getIntent().getStringExtra("title");

            // Attempt to remove the movie
            boolean removed = removeMovieFromWatchList(titleToRemove);

            // Feedback for the user based on whether the movie was found and removed
            if (removed) {
                Toast.makeText(this, "Removed from watchlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Movie not found in watchlist", Toast.LENGTH_SHORT).show();
            }
        });


        // Set up the back button to close the activity
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());

        // Retrieve data from the intent
        String title = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String description = getIntent().getStringExtra("description");

        // Find views
        TextView titleTextView = findViewById(R.id.title_text_view);
        ImageView movieImageView = findViewById(R.id.movie_image_view);
        TextView descriptionTextView = findViewById(R.id.description_text_view);

        // Set data to views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        Glide.with(this).load(imageUrl).into(movieImageView);
    }

    private boolean removeMovieFromWatchList(String title) {
        for (MovieItem movie : watchList) {
            if (movie.getTitle().equals(title)) {
                watchList.remove(movie);
                return true;
            }
        }
        return false;
    }
}
