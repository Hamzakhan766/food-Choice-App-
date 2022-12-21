package com.example.foodchoice.HelperClasses;

public class RecipeModel {
    String title;
    Integer thumbnailImage;

    public RecipeModel(String title, Integer thumbnailImage) {
        this.title = title;
        this.thumbnailImage = thumbnailImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(Integer thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
}
