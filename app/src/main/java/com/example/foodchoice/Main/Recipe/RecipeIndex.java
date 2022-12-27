package com.example.foodchoice.Main.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        recipeIndexBinding.AddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipeIndex.this,AddRecipe.class));
            }
        });


    }
}