package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderRegion {

    @SerializedName("flatrate")
    List<ProviderResult> subscriptionsproviderResult;

    @SerializedName("buy")
    List<ProviderResult> buyproviderResult;

    @SerializedName("rent")
    List<ProviderResult> rentproviderResult;

    public List<ProviderResult> getSubscriptionsproviderResult() {
        return subscriptionsproviderResult;
    }

    public List<ProviderResult> getBuyproviderResult() {
        return buyproviderResult;
    }

    public List<ProviderResult> getRentproviderResult() {
        return rentproviderResult;
    }
}
