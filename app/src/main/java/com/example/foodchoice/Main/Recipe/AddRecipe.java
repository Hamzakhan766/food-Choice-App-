package com.example.foodchoice.Main.Recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.databinding.ActivityAddRecipeBinding;

public class AddRecipe extends AppCompatActivity {
    ActivityAddRecipeBinding addRecipeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addRecipeBinding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(addRecipeBinding.getRoot());





       addRecipeBinding.nextStep.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               callNextScreen();
           }
       });
    }

    public void callNextScreen(){

        Intent intent = new Intent(AddRecipe.this,StepTwo.class);
        intent.putExtra("RecipeName",addRecipeBinding.RecipeName.getEditText().getText().toString());
        intent.putExtra("RecipeDescription",addRecipeBinding.RecipeDescription.getEditText().getText().toString());
        intent.putExtra("RecipeDirection",addRecipeBinding.RecipeDirection.getEditText().getText().toString());


        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View , String>(addRecipeBinding.textChange,"titleChange");
        pairs[1] = new Pair<View , String>(addRecipeBinding.nextStep,"nextActivity");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddRecipe.this,pairs);
        startActivity(intent,options.toBundle());

    }


}