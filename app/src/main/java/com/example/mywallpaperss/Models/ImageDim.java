package com.example.mywallpaperss.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

        public class ImageDim {
        @SerializedName("medium")
        @Expose
        private String medium;

        @SerializedName("large")
        @Expose
        private String large;

        public ImageDim(String medium, String large) {
            this.medium = medium;
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }