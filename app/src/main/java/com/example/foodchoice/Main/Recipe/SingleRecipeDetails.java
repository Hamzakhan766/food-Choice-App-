package com.example.foodchoice.Main.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.foodchoice.databinding.ActivitySingleRecipeDetailsBinding;

public class SingleRecipeDetails extends AppCompatActivity {
    ActivitySingleRecipeDetailsBinding singleRecipeDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        singleRecipeDetailsBinding =ActivitySingleRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(singleRecipeDetailsBinding.getRoot());

        //////get details form single recipe items//////
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("SingleRecipeImage")).into(singleRecipeDetailsBinding.SingleRecipeImage);
        singleRecipeDetailsBinding.SingleRecipeName.setText(getIntent().getStringExtra("SingleRecipeName"));
        singleRecipeDetailsBinding.SingleRecipeDescription.setText(getIntent().getStringExtra("SingleRecipeDescription"));
        singleRecipeDetailsBinding.SingleRecipeServing.setText(getIntent().getStringExtra("SingleRecipeServing"));
        singleRecipeDetailsBinding.SingleRecipeTiming.setText(getIntent().getStringExtra("SingleRecipeTiming"));
        singleRecipeDetailsBinding.SingleRecipeDirection.setText(getIntent().getStringExtra("SingleRecipeDirection"));


    }
}