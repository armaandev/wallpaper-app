package com.example.mywallpaperss;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;

public class FullImagesActivity extends AppCompatActivity {
     private ImageView imageView,share;
     private ProgressBar progressBar;
     private Button save,background;
     private String imageUrl="",dirPath, fileName;
     File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_images);

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        imageView = findViewById(R.id.imageView2);
        progressBar = findViewById(R.id.progressBar);
        background = findViewById(R.id.button);
        save = findViewById(R.id.btnSave);
        share = findViewById(R.id.btnShare);

        imageUrl = getIntent().getStringExtra("imageUrl");

        loadImage(imageUrl);

        AndroidNetworking.initialize(getApplicationContext());

        //Folder Creating Into Phone Storage
        dirPath = Environment.getExternalStorageDirectory() + "/Image";

        fileName = "image.jpeg";

        //file Creating With Folder & Fle Name
        file = new File(dirPath, fileName);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHomeScreen();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FullImagesActivity.this, "save hello", Toast.LENGTH_SHORT).show();
                saveImage(imageUrl);
            }
        });
         share.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(FullImagesActivity.this, "Share button is clicked", Toast.LENGTH_SHORT).show();

                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_SEND);
                 sendIntent.putExtra(Intent.EXTRA_TEXT, imageUrl);
                 sendIntent.setType("text/plain");
//                 Log.d("Armaan",imageUrl);

                 Intent shareIntent = Intent.createChooser(sendIntent, null);
                 startActivity(shareIntent);
             }
         });
    }

    private void saveImage(String imageUrl) {
        AndroidNetworking.download(this.imageUrl, dirPath, fileName)
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        Toast.makeText(FullImagesActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void setHomeScreen() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Bitmap bitmap  = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "Wallpaper Set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadImage(String imageUrl) {

        Glide.with(this)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

    }

}