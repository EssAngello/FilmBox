package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

public class CastResult {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("profile_path")
    String profilePath;

    @SerializedName("character")
    String character;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getCharacter() { return character; }

}
