package com.example.foodchoice.Main.Recipe;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoice.databinding.ActivityRecipeIndexBinding;

public class RecipeIndex extends AppCompatActivity {
    ActivityRecipeIndexBinding recipeIndexBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recipeIndexBinding = ActivityRecipeIndexBinding.inflate(getLayoutInflater());
        setContentView(recipeIndexBinding.getRoot());
    }
}