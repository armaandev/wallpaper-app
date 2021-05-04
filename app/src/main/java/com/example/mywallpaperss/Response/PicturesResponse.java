package com.example.mywallpaperss.Response;

import com.example.mywallpaperss.Models.Pictures;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PicturesResponse {
    @SerializedName("photos")
    private List<Pictures> picturesList;

    public PicturesResponse(List<Pictures> picturesList) {
        this.picturesList = picturesList;
    }

    public List<Pictures> getPicturesList() {
        return picturesList;
    }

    public void setPicturesList(List<Pictures> picturesList) {
        this.picturesList = picturesList;
    }
}