package com.example.daily.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.daily.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {


    Context context;
    ArrayList<Categories> categoriesArrayList;
    ClickedItem clickedItem;

    public CategoriesAdapter(Context context, ArrayList<Categories> categoriesArrayList, ClickedItem clickedItem) {
        this.context = context;
        this.categoriesArrayList = categoriesArrayList;
        this.clickedItem = clickedItem;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoriesAdapter.ViewHolder holder, int position) {
        Categories categories=categoriesArrayList.get(position);
        holder.Category_Text.setText(categories.getCategory_Title());
        Glide.with(context).load(categories.getCategory_Image()).into(holder.Category_Image);

    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Category_Image;
        TextView Category_Text;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Category_Image = itemView.findViewById(R.id.Category_Image);
            Category_Text = itemView.findViewById(R.id.Category_Text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedItem.ClickedItem(getAdapterPosition());
                }
            });
        }
    }

    public interface ClickedItem{
        void ClickedItem(int position);
    }
}
