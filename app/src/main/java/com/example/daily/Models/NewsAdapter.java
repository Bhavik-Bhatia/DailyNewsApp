package com.example.daily.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.daily.DetailedNews;
import com.example.daily.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Articles> articlesArrayList;

    public NewsAdapter(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articlesArrayList = articles;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //Getting xml R.layout.news_item to java by inflating
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new NewsAdapter.ViewHolder(view);
    }

    //Here binding of data and view happens one by one
    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsAdapter.ViewHolder holder, int position) {
        //Now we have all data in articles object which is in a Arraylist
        //We get the first object with position var

        Articles articles = articlesArrayList.get(position);
        holder.News_Text.setText(articles.getTitle());
        holder.News_SubText.setText(articles.getDescription());
        Glide.with(context).load(articles.getUrlToImage()).into(holder.News_Image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DetailedNews.class);
                i.putExtra("Title",articles.getTitle());
                i.putExtra("Subtext",articles.getDescription());
                i.putExtra("URL",articles.getUrl());
                i.putExtra("URLImage",articles.getUrlToImage());
                i.putExtra("Content",articles.getContent());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView News_Image;
        TextView News_Text,News_SubText;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //Passing that view here so we can find the view elements
            News_Image = itemView.findViewById(R.id.News_Image);
            News_Text = itemView.findViewById(R.id.News_Text);
            News_SubText = itemView.findViewById(R.id.News_SubText);
        }
    }
}
