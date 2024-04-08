package com.example.mymovie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieItem> movieItems;

    public MovieAdapter(Context context, List<MovieItem> movieItems) {
        this.context = context;
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieItem movieItem = movieItems.get(position);
        holder.movieTitle.setText(movieItem.getTitle());
        Glide.with(context).load(movieItem.getImageUrl()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public void updateMovieItems(List<MovieItem> newItems) {
        movieItems.clear();
        movieItems.addAll(newItems);
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;

        MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < movieItems.size()) {
                        MovieItem movie = movieItems.get(position);

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("title", movie.getTitle());
                        intent.putExtra("imageUrl", movie.getImageUrl());
                        intent.putExtra("description", movie.getDescription());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
