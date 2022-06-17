package com.example.daily.Models;

public class Categories {
    private String category_Title;
    private String category_Image;

    public Categories(String category_Title, String category_Image) {
        this.category_Title = category_Title;
        this.category_Image = category_Image;
    }

    public String getCategory_Title() {
        return category_Title;
    }

    public void setCategory_Title(String category_Title) {
        this.category_Title = category_Title;
    }

    public String getCategory_Image() {
        return category_Image;
    }

    public void setCategory_Image(String category_Image) {
        this.category_Image = category_Image;
    }
}
