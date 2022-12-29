package com.example.foodchoice.HelperClasses;

public class RecipeModel {
  String recipeID,recipeName,recipeDirection,recipeDiscription,userID,recipeImageUrl,recipeCategoryID,recipeVideoUrl;

    public RecipeModel(String recipeID, String recipeName, String recipeDirection, String recipeDiscription, String userID, String recipeImageUrl, String recipeCategoryID, String recipeVideoUrl) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeDirection = recipeDirection;
        this.recipeDiscription = recipeDiscription;
        this.userID = userID;
        this.recipeImageUrl = recipeImageUrl;
        this.recipeCategoryID = recipeCategoryID;
        this.recipeVideoUrl = recipeVideoUrl;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDirection() {
        return recipeDirection;
    }

    public void setRecipeDirection(String recipeDirection) {
        this.recipeDirection = recipeDirection;
    }

    public String getRecipeDiscription() {
        return recipeDiscription;
    }

    public void setRecipeDiscription(String recipeDiscription) {
        this.recipeDiscription = recipeDiscription;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRecipeImageUrl() {
        return recipeImageUrl;
    }

    public void setRecipeImageUrl(String recipeImageUrl) {
        this.recipeImageUrl = recipeImageUrl;
    }

    public String getRecipeCategoryID() {
        return recipeCategoryID;
    }

    public void setRecipeCategoryID(String recipeCategoryID) {
        this.recipeCategoryID = recipeCategoryID;
    }

    public String getRecipeVideoUrl() {
        return recipeVideoUrl;
    }

    public void setRecipeVideoUrl(String recipeVideoUrl) {
        this.recipeVideoUrl = recipeVideoUrl;
    }
}
