package com.example.foodchoice.Main.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.foodchoice.databinding.ActivityBreakfastRecipeBinding;

public class BreakfastRecipe extends AppCompatActivity {
    ActivityBreakfastRecipeBinding breakfastRecipeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        breakfastRecipeBinding = ActivityBreakfastRecipeBinding.inflate(getLayoutInflater());
        setContentView(breakfastRecipeBinding.getRoot());
    }
}