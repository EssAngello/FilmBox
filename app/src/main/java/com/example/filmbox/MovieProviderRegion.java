package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieProviderRegion {

    @SerializedName("ES")
    private ProviderRegion providerRegion;

    public ProviderRegion getProviderRegion() {
        return providerRegion;
    }
}
