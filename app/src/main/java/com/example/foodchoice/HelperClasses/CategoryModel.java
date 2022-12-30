package com.example.foodchoice.HelperClasses;

public class CategoryModel {
    String categoryName;
    String categoryDescription;
    String categoryImageUri;

    public CategoryModel(){

    }

    public CategoryModel(String categoryName, String categoryImageUri,String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryImageUri = categoryImageUri;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImageUri() {
        return categoryImageUri;
    }

    public void setCategoryImageUri(String categoryImageUri) {
        this.categoryImageUri = categoryImageUri;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
