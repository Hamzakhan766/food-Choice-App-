package com.example.foodchoice.Main.MealPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodchoice.databinding.ActivityCrBrRecipeBinding;

public class CrBrRecipe extends AppCompatActivity {
   ActivityCrBrRecipeBinding crBrRecipeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crBrRecipeBinding = ActivityCrBrRecipeBinding.inflate(getLayoutInflater());
        setContentView(crBrRecipeBinding.getRoot());
    }
}