package com.example.daily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.daily.Models.Articles;
import com.example.daily.Models.Categories;
import com.example.daily.Models.CategoriesAdapter;
import com.example.daily.Models.MyApi;
import com.example.daily.Models.NewsAdapter;
import com.example.daily.Models.NewsModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoriesAdapter.ClickedItem {
    //820290d4c840415aa7aa4cab535d8bc7
    Group loadingGroup;
    LottieAnimationView loading;
    RecyclerView CategoriesRv,NewsRv;

    ArrayList<Articles> articlesArrayList;
    ArrayList<Categories> categoriesArrayList;

    NewsAdapter newsAdapter;
    CategoriesAdapter categoriesAdapter;
    String[] category_Title = {"All",
            "Technology","Science","Sports","General","Business","Entertainment","Health"};
    String[] category_Image = {
                                "https://images.unsplash.com/photo-1546422904-90eab23c3d7e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=872&q=80"
                               ,"https://images.unsplash.com/photo-1461749280684-dccba630e2f6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dGVjaG5vbG9naWVzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60"
                               ,"https://images.unsplash.com/photo-1564325724739-bae0bd08762c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
                               ,"https://images.unsplash.com/photo-1517649763962-0c623066013b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c3BvcnRzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60"
                               ,"https://images.unsplash.com/photo-1512314889357-e157c22f938d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80"
                               ,"https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
                               ,"https://images.unsplash.com/photo-1514525253161-7a46d19cd819?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                               ,"https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&q=80"};

    String category="All";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingGroup = findViewById(R.id.loadingGroup);

        loading = findViewById(R.id.loadingAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                loadingGroup.setVisibility(View.VISIBLE);
            }
        },2050);

        CategoriesRv = findViewById(R.id.CategoriesRv);
        NewsRv = findViewById(R.id.NewsRv);
        categoriesArrayList=new ArrayList<>();
        articlesArrayList=new ArrayList<>();

        //Pass that ArrayList to the adapter with Context
        categoriesAdapter = new CategoriesAdapter(this,categoriesArrayList,this::ClickedItem);
        newsAdapter= new NewsAdapter(this,articlesArrayList);


        //Get data from News API one by one store in object then in ArrayList
        for (int i=0;i<category_Title.length;i++){
            Categories categories = new Categories(category_Title[i],category_Image[i]);
            try {
                categoriesArrayList.add(categories);
            }catch (NullPointerException e) {
                Log.e("Exception: ",e+"");
            }
        }

        //Work with Retrofit
        getNews(category);

        CategoriesRv.setAdapter(categoriesAdapter);
        NewsRv.setLayoutManager(new LinearLayoutManager(this));
        NewsRv.setAdapter(newsAdapter);


    }

    private void getNews(String category) {
        articlesArrayList.clear();

        String categoryUrl="https://newsapi.org/v2/top-headlines?category="+category+"&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=820290d4c840415aa7aa4cab535d8bc7";
        String baseUrl="https://newsapi.org/v2/";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=820290d4c840415aa7aa4cab535d8bc7";

        //Retrofit Object
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApi myApi=retrofit.create(MyApi.class);
        Call<NewsModel> call;
        //Check if news is all or a specific category
        if (category.equals("All")){
            call=myApi.getAllNews(url);
        }else {
            call=myApi.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                ArrayList<Articles> aArrayList=newsModel.getArticles();

                for (Articles a:aArrayList){
                    Articles articles = new Articles(a.getTitle(),a.getDescription(),a.getUrl(),a.getUrlToImage(),a.getContent());
                    articlesArrayList.add(articles);
                }
                newsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure getting news",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void ClickedItem(int position) {
        Categories categories = categoriesArrayList.get(position);
        getNews(categories.getCategory_Title());

    }
}