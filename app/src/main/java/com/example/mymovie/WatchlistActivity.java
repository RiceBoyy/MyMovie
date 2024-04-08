package com.example.mymovie;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WatchlistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());

        recyclerView = findViewById(R.id.watchlist_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this, DetailActivity.watchList);
        recyclerView.setAdapter(movieAdapter);
    }
}

