package com.example.foodchoice.Main.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodchoice.databinding.ActivityMealPlannerBinding;

public class MealPlanner extends AppCompatActivity {
   ActivityMealPlannerBinding mealPlannerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mealPlannerBinding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(mealPlannerBinding.getRoot());
    }
}