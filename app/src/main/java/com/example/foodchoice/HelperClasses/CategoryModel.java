package com.example.foodchoice.HelperClasses;

public class CategoryModel {
    String categoryName , categoryImageUri;

    public CategoryModel(){

    }

    public CategoryModel(String categoryName, String categoryImageUri) {
        this.categoryName = categoryName;
        this.categoryImageUri = categoryImageUri;
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


}
