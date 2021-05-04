package com.example.mywallpaperss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.mywallpaperss.Adapters.PicturesAdapter;
import com.example.mywallpaperss.Adapters.RecyclerViewClickInterface;
import com.example.mywallpaperss.Api.PicturesApiInterface;
import com.example.mywallpaperss.Api.RetrofitClient;
import com.example.mywallpaperss.Models.Pictures;
import com.example.mywallpaperss.Response.PicturesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewClickInterface {
    private RecyclerView picRecyclerView;
    private final String  API_KEY="563492ad6f91700001000001e395407daa4a4668a1a8d766eb934717";
    private int pageCount =1;
    private static int perPage = 15;
    private List<Pictures> picturesList = new ArrayList<>();
    private NestedScrollView scrollView;
    private SearchView searchView;
    ProgressBar progressBar;
    private String querySearch;
    RecyclerViewClickInterface recyclerViewClickInterface;
    CardView nature,wildlife,technology,education,love, popular,eid;
    private PicturesApiInterface apiService;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        picRecyclerView = findViewById(R.id.recyclerView);
//       scrollView =findViewById(R.id.nestdScrollView);


        nature=findViewById(R.id.nature);
        wildlife=findViewById(R.id.wildlife);
        education=findViewById(R.id.education);
        technology=findViewById(R.id.technologies);
        love=findViewById(R.id.love);
        popular=findViewById(R.id.popular);
        eid=findViewById(R.id.eid);


        searchView=findViewById(R.id.etSearch);
        progressBar=findViewById(R.id.progressBar);
        querySearch=searchView.getQuery().toString().toLowerCase();

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                fetchApiData();
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty()){
                    fetchApiData();
                }
                else {
                    searchData(newText);
                }
                return true;

            }
        });

        picRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        picRecyclerView.setLayoutManager(gridLayoutManager);

        popular.setOnClickListener(this);
        nature.setOnClickListener(this);
        wildlife.setOnClickListener(this);
        education.setOnClickListener(this);
        love.setOnClickListener(this);
        technology.setOnClickListener(this);
        eid.setOnClickListener(this);

        recyclerViewClickInterface=(RecyclerViewClickInterface)MainActivity.this;
        
        fetchApiData();
//      setPagination(true);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popular:
                fetchApiData();
                break;
            case R.id.nature:
                searchData("nature");
                break;
            case R.id.wildlife:
                searchData("wildlife");
                break;
            case R.id.love:
                searchData("love");
                break;
            case R.id.technologies:
                searchData("technologies");
                break;
            case R.id.education:
                searchData("education");
                break;
            case R.id.eid:
                searchData("eid");
                break;

        }
    }

    private void searchData(String query) {
//        Call<PicturesResponse> call = RetrofitClient
//                .getInstance()
//                .getApi().getSearch(API_KEY,query);
        apiService = RetrofitClient.getClient().create(PicturesApiInterface.class);
        Call<PicturesResponse> call = apiService.getSearch(API_KEY,query);

        call.enqueue(new Callback<PicturesResponse>() {
            @Override
            public void onResponse(Call<PicturesResponse> call, Response<PicturesResponse> response) {
                PicturesResponse picturesResponse = response.body();
                if(response.isSuccessful()){
                 picturesList=response.body().getPicturesList();

                    PicturesAdapter picturesAdapter =new PicturesAdapter(picturesList,getApplicationContext(),recyclerViewClickInterface);
                    picRecyclerView.setAdapter(picturesAdapter);
                    picturesAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PicturesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

//    private void setPagination(boolean isPaginationAllowed) {
//        if(isPaginationAllowed){
//            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
//                        fetchApiData(++pageCount);
//                        Toast.makeText(MainActivity.this, "new page loaded"+pageCount, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//        else{
//            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                }
//            });
//
//        }
//    }

    private void fetchApiData() {
        apiService = RetrofitClient.getClient().create(PicturesApiInterface.class);
        Call<PicturesResponse> call = apiService.getPictures(API_KEY);
        call.enqueue(new Callback<PicturesResponse>() {
            @Override
            public void onResponse(Call<PicturesResponse> call, Response<PicturesResponse> response) {
                PicturesResponse picturesResponse = response.body();

             if(response.isSuccessful()) {
                 picturesList.addAll(picturesResponse.getPicturesList());

                 PicturesAdapter picturesAdapter = new PicturesAdapter(picturesList, getApplicationContext(), MainActivity.this);
                 picRecyclerView.setAdapter(picturesAdapter);
                 picturesAdapter.notifyDataSetChanged();

             }
             else{
                 Toast.makeText(MainActivity.this, "Pictures did'nt Load", Toast.LENGTH_SHORT).show();
             }
            }

            @Override
            public void onFailure(Call<PicturesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


//      Call<PicturesResponse> call = RetrofitClient.getInstance().getApi().getPictures(API_KEY,pageCount,perPage);
//        Call<PicturesResponse> call = RetrofitClient.getInstance().getApi().getPictures(API_KEY);
//        call.enqueue(new Callback<PicturesResponse>() {
//            @Override
//            public void onResponse(Call<PicturesResponse> call, Response<PicturesResponse> response) {
//             PicturesResponse picturesResponse = response.body();
//             if(response.isSuccessful()){
//                 picturesList.addAll(picturesResponse.getPicturesList());
//
//                 PicturesAdapter picturesAdapter = new PicturesAdapter(picturesList, getApplicationContext(),MainActivity.this);
//                 picRecyclerView.setAdapter(picturesAdapter);
//                 picturesAdapter.notifyDataSetChanged();
//             }
//             else{
//                 Toast.makeText(MainActivity.this, "Pictures did'nt Load", Toast.LENGTH_SHORT).show();
//             }
//            }
//
//            @Override
//            public void onFailure(Call<PicturesResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getBaseContext(),FullImagesActivity.class);
         intent.putExtra("imageUrl",picturesList.get(position).getSrc().getLarge());
         startActivity(intent);
    }


}