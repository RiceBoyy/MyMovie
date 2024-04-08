    package com.example.mymovie;

    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    import okhttp3.Call;
    import okhttp3.Callback;
    import okhttp3.OkHttpClient;
    import okhttp3.Request;
    import okhttp3.Response;

    public class MainActivity extends AppCompatActivity {

        private static final String JSON_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=7a95057ee222813d9e21e89b539d1282";
        private static final String TAG = "MainActivity";
        private RecyclerView recyclerView;
        private MovieAdapter movieAdapter;
        private List<MovieItem> movieItems;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            List<MovieItem> watchList = DetailActivity.watchList;
            recyclerView = findViewById(R.id.movies_recycler_view);
            int numberOfColumns = 4;
            recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
            recyclerView.setHasFixedSize(true);

            movieItems = new ArrayList<>();
            movieAdapter = new MovieAdapter(this, movieItems );
            recyclerView.setAdapter(movieAdapter);

            Button viewWatchlistButton = findViewById(R.id.view_watchlist_button);
            viewWatchlistButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, WatchlistActivity.class);
                    startActivity(intent);
                }
            });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
                v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                        insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                        insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                        insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
                return insets;
            });

            fetchMovieData();
        }

        private void fetchMovieData() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(JSON_URL)
                    .addHeader("Accept", "application/json")
                    .addHeader("api_key", "7a95057ee222813d9e21e89b539d1282")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "An error occurred", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String responseData = response.body().string();
                        new Handler(Looper.getMainLooper()).post(() -> {
                            try {
                                List<MovieItem> newItems = new ArrayList<>();
                                JSONObject jsonObject = new JSONObject(responseData);
                                JSONArray resultsArray = jsonObject.getJSONArray("results");
                                for (int i = 0; i < resultsArray.length(); i++) {
                                    JSONObject movieJson = resultsArray.getJSONObject(i);
                                    String title = movieJson.getString("title");
                                    String backdropPath = movieJson.getString("backdrop_path");
                                    String overview = movieJson.getString("overview");
                                    String imageUrl = "https://image.tmdb.org/t/p/w500" + backdropPath;

                                    MovieItem movieItem = new MovieItem(title, imageUrl, overview);
                                    newItems.add(movieItem);
                                }

                                movieAdapter.updateMovieItems(newItems);
                            } catch (Exception e) {
                                Log.e(TAG, "JSON parsing error", e);
                            }
                        });
                    } else {
                        Log.e(TAG, "Request not successful");
                    }
                }
            });
        }

    }
