package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieCredits {

    @SerializedName("crew")
    private final List<CrewResult> results = new ArrayList<>();

    @SerializedName("cast")
    private final List<CastResult> resultscast = new ArrayList<>();


    public List<CrewResult> getResults() {
        return results;
    }
    public List<CastResult> getResultsCast() {
        return resultscast;
    }

}
