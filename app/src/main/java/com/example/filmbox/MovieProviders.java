package com.example.filmbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieProviders {

    @SerializedName("results")
    private MovieProviderRegion movieProviderRegion;

    public MovieProviderRegion getMovieProviderRegion() {
        return movieProviderRegion;
    }
}
