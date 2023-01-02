package com.example.foodchoice.HelperClasses;

public class RecipeModel {
    String recipeID;
    String recipeName;
    String recipeDirection;
    String recipeDescription;
    String recipeServing;
    String userID;
    String RecipeIngredients;
    String recipeImageUrl;
    String recipeCategoryID;
    String recipeVideoUrl;

    public RecipeModel() {
    }

    public RecipeModel(String recipeID, String recipeName, String recipeDescription, String recipeServing, String userID, String recipeImageUrl, String recipeCategoryID) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeServing = recipeServing;
        this.userID = userID;
        this.recipeImageUrl = recipeImageUrl;
        this.recipeCategoryID = recipeCategoryID;
    }

    public RecipeModel(String recipeID, String recipeName, String recipeDirection, String recipeDescription, String recipeServing, String recipeIngredients , String userID, String recipeImageUrl, String recipeCategoryID, String recipeVideoUrl) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeDirection = recipeDirection;
        this.recipeDescription = recipeDescription;
        this.recipeServing = recipeServing;
        RecipeIngredients = recipeIngredients;
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

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }


    public String getRecipeServing() {
        return recipeServing;
    }

    public void setRecipeServing(String recipeServing) {
        this.recipeServing = recipeServing;
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

    public String getRecipeIngredients() {
        return RecipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        RecipeIngredients = recipeIngredients;
    }
}
