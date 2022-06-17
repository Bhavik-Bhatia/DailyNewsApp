package com.example.daily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedNews extends AppCompatActivity {
    TextView Detailed_News_Text,Detailed_News_SubText,Detailed_News_Content;
    ImageView Detailed_News_Image;
    Button ReadMoreBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

        Detailed_News_Text = findViewById(R.id.Detailed_News_Text);
        Detailed_News_SubText = findViewById(R.id.Detailed_News_SubText);
        Detailed_News_Content = findViewById(R.id.Detailed_News_Content);
        Detailed_News_Image = findViewById(R.id.Detailed_News_Image);
        ReadMoreBtn = findViewById(R.id.ReadMoreBtn);

        Detailed_News_Text.setText(getIntent().getStringExtra("Title"));
        Detailed_News_SubText.setText(getIntent().getStringExtra("Subtext"));
        Detailed_News_Content.setText(getIntent().getStringExtra("Content"));
        Glide.with(DetailedNews.this).load(getIntent().getStringExtra("URLImage")).into(Detailed_News_Image);


        ReadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse(getIntent().getStringExtra("URL")));
                startActivity(it);
            }
        });

    }
}