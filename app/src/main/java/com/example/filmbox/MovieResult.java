package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

public class MovieResult {

    @SerializedName("id")
    int id;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("title")
    String title;

    @SerializedName("release_date")
    String date;

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

}
