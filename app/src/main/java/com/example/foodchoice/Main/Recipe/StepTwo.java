package com.example.foodchoice.Main.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoice.databinding.ActivityStepTwoBinding;

import java.util.Objects;

public class StepTwo extends AppCompatActivity {
    ActivityStepTwoBinding stepTwoBinding;
    private final int Image_RequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        stepTwoBinding = ActivityStepTwoBinding.inflate(getLayoutInflater());
        setContentView(stepTwoBinding.getRoot());

        Intent formAct = getIntent();
        String RecipeName = formAct.getStringExtra("RecipeName");
        String RecipeDescription = formAct.getStringExtra("RecipeDescription");
        String RecipeDirection = formAct.getStringExtra("RecipeDirection");


        stepTwoBinding.uploadCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        stepTwoBinding.uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe();
            }
        });

    }

    private void uploadRecipe() {
    }

    private void selectImage() {
        Intent getImage = new Intent(Intent.ACTION_PICK);
        getImage.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getImage, Image_RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Image_RequestCode) {
                stepTwoBinding.RecipeCoverImage.setImageURI(Objects.requireNonNull(data).getData());
            }
        }


    }
}