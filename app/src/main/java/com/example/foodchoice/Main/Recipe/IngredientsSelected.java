package com.example.foodchoice.Main.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodchoice.databinding.ActivityIngrediantsSelectedBinding;

public class IngredientsSelected extends AppCompatActivity {
     ActivityIngrediantsSelectedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIngrediantsSelectedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}