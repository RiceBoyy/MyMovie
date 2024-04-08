package com.example.mymovie;

public class MovieItem {
    private String title;
    private String imageUrl;
    private String description;

    // Constructor
    public MovieItem(String title, String imageUrl, String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}