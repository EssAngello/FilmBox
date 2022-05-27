package com.example.filmbox;

import com.google.gson.annotations.SerializedName;

public class CrewResult {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("job")
    String job;

    @SerializedName("profile_path")
    String profilePath;

    @SerializedName("department")
    String department;



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getDepartment() {
        return department;
    }
}
