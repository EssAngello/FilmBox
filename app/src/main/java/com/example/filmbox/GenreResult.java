package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

public class GenreResult {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
