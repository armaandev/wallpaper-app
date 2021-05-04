package com.example.mywallpaperss.Api;

import com.example.mywallpaperss.Response.PicturesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PicturesApiInterface {
//    @GET("curated")
//    Call<PicturesResponse> getPictures(
//            @Header("Authorization" )String credentials,
//            @Query("page") int pageCount,
//            @Query("per_page") int perPage
//    );

    @GET("curated/?page=1&per_page=80")
    Call<PicturesResponse> getPictures(
            @Header("Authorization" )String credentials
    );


    @GET("search?page=1&per_page=80")
    Call<PicturesResponse> getSearch(
     @Header("Authorization") String credentials,
     @Query("query") String querySearch
    );
}
