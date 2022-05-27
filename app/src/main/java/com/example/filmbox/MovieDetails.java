package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails {

    @SerializedName("title")
    String title;

    @SerializedName("genres")
    List<GenreResult> genres = new ArrayList<>();

    @SerializedName("overview")
    String overview;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("release_date")
    String releaseDate;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("tagline")
    String tagline;


    public List<GenreResult> getGenres() { return genres;}

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

}
