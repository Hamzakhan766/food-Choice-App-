package com.example.foodchoice.Main.MealPlanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoice.databinding.ActivityMealPlannerBinding;

public class MealPlanner extends AppCompatActivity {
    ActivityMealPlannerBinding mealPlannerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mealPlannerBinding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(mealPlannerBinding.getRoot());

        mealPlannerBinding.breakfastCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(MealPlanner.this,BreakfastRecipe.class));
            }
        });

        mealPlannerBinding.lunchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MealPlanner.this,LunchRecipe.class));
            }
        });


        mealPlannerBinding.dinnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MealPlanner.this,DinnerRecipe.class));
            }
        });

    }
}