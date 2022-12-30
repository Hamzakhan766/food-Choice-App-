package com.example.foodchoice.Main.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.foodchoice.databinding.ActivityDinnerRecipeBinding;

public class DinnerRecipe extends AppCompatActivity {
   ActivityDinnerRecipeBinding dinnerRecipeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dinnerRecipeBinding = ActivityDinnerRecipeBinding.inflate(getLayoutInflater());
        setContentView(dinnerRecipeBinding.getRoot());
    }
}