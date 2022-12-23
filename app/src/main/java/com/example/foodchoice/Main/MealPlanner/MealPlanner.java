package com.example.foodchoice.Main.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.foodchoice.databinding.ActivityMealPlannerBinding;

public class MealPlanner extends AppCompatActivity {
   ActivityMealPlannerBinding mealPlannerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mealPlannerBinding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(mealPlannerBinding.getRoot());

        mealPlannerBinding.breakfastCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mealPlannerBinding.lunchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        mealPlannerBinding.dinnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}