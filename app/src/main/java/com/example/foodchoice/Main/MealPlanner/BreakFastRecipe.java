package com.example.foodchoice.Main.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.foodchoice.databinding.ActivityBreakFastRecipeBinding;

public class BreakFastRecipe extends AppCompatActivity {
    ActivityBreakFastRecipeBinding breakFastRecipeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        breakFastRecipeBinding = ActivityBreakFastRecipeBinding.inflate(getLayoutInflater());
        setContentView(breakFastRecipeBinding.getRoot());


        breakFastRecipeBinding.backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onBackPressed();
            }
        });

        breakFastRecipeBinding.addBreakFastRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}