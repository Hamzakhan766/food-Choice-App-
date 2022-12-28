package com.example.foodchoice.Main.Recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoice.AdapterClasses.CategoryAdapter;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivityStepTwoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StepTwo extends AppCompatActivity {
    ActivityStepTwoBinding stepTwoBinding;
    private final int Image_RequestCode = 1;
    DatabaseReference reference;
    ArrayList<Integer> cat_id;
    ArrayList<String> cat_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        stepTwoBinding = ActivityStepTwoBinding.inflate(getLayoutInflater());
        setContentView(stepTwoBinding.getRoot());

//        Intent formAct = getIntent();
//        String RecipeName = formAct.getStringExtra("RecipeName");
//        String RecipeDescription = formAct.getStringExtra("RecipeDescription");
//        String RecipeDirection = formAct.getStringExtra("RecipeDirection");


        reference = FirebaseDatabase.getInstance().getReference("Categories");
        cat_id = new ArrayList<Integer>();
        cat_Name = new ArrayList<String>();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cat_Name);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("category loop","abc");
                for (DataSnapshot ds : snapshot.getChildren()){
                    cat_id.add(Integer.parseInt(String.valueOf(ds.child("categoryId").getValue())));
                    cat_Name.add(String.valueOf(ds.child("categoryName").getValue()));
                    Log.d("category loop", String.valueOf(ds.child("categoryName").getValue()));
                }
                stepTwoBinding.catList.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        

        stepTwoBinding.uploadCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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







//    private void selectImage() {
//        Intent getImage = new Intent(Intent.ACTION_PICK);
//        getImage.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(getImage,Image_RequestCode);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode == RESULT_OK){
//             if(requestCode == Image_RequestCode){
//                   stepTwoBinding.RecipeCoverImage.setImageURI(Objects.requireNonNull(data).getData());
//             }
//        }
//
//
//    }
}