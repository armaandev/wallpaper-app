package com.example.mywallpaperss.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pictures {

    @SerializedName("src")
    @Expose
    private ImageDim src;

    public Pictures(ImageDim src) {
        this.src = src;
    }

    public ImageDim getSrc() {
        return src;
    }

    public void setSrc(ImageDim src) {
        this.src = src;
    }


}
