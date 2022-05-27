package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

public class ProviderResult {

    @SerializedName("provider_id")
    int id;

    @SerializedName("provider_name")
    String name;


    @SerializedName("logo_path")
    String logoPath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoPath() {
        return logoPath;
    }
}
