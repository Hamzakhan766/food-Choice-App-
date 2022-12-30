package com.example.foodchoice.Main.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodchoice.databinding.ActivitySingleRecipeDetailsBinding;

public class SingleRecipeDetails extends AppCompatActivity {
    ActivitySingleRecipeDetailsBinding singleRecipeDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singleRecipeDetailsBinding =ActivitySingleRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(singleRecipeDetailsBinding.getRoot());
    }
}