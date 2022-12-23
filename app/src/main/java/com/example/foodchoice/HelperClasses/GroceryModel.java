package com.example.foodchoice.HelperClasses;

public class GroceryModel {
    String groceryName, groceryImageUrl;

    //default constructor///
    public GroceryModel(){

    }

    public GroceryModel(String groceryName, String groceryImageUrl) {
        this.groceryName = groceryName;
        this.groceryImageUrl = groceryImageUrl;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public void setGroceryName(String groceryName) {
        this.groceryName = groceryName;
    }

    public String getGroceryImageUrl() {
        return groceryImageUrl;
    }

    public void setGroceryImageUrl(String groceryImageUrl) {
        this.groceryImageUrl = groceryImageUrl;
    }
}
