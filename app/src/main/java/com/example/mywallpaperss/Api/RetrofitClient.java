package com.example.mywallpaperss.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    public static final String BASE_URL = "https://api.pexels.com/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1, Protocol.SPDY_3))
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}