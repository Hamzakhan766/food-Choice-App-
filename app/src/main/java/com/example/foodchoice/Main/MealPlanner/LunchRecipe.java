package com.example.foodchoice.Main.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.foodchoice.databinding.ActivityLunchRecipeBinding;

public class LunchRecipe extends AppCompatActivity {
   ActivityLunchRecipeBinding lunchRecipeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lunchRecipeBinding = ActivityLunchRecipeBinding.inflate(getLayoutInflater());
        setContentView(lunchRecipeBinding.getRoot());
    }
}