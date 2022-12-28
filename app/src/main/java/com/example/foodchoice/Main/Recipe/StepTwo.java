package com.example.foodchoice.Main.Recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodchoice.databinding.ActivityStepTwoBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class StepTwo extends AppCompatActivity {
    ActivityStepTwoBinding stepTwoBinding;
    private final int Image_RequestCode = 100;
    Uri imageUri = null;
    DatabaseReference reference,userInfo;
    String name,uid;
    ArrayList<Integer> cat_id;
    ArrayList<String> cat_Name;
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

        Log.d("my result", RecipeName + RecipeDescription + RecipeDirection);


         ////userInfo for post recipe////
        userInfo = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userInfo.orderByChild("user_id").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    name = "" + ds.child("user_UserName").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //////set spinner adapter fpr category//////////
        reference = FirebaseDatabase.getInstance().getReference("Categories");
        cat_id = new ArrayList<Integer>();
        cat_Name = new ArrayList<String>();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cat_Name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    cat_id.add(Integer.parseInt(String.valueOf(ds.child("categoryId").getValue())));
                    cat_Name.add(String.valueOf(ds.child("categoryName").getValue()));
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
                pickFromGallery();
            }
        });

        stepTwoBinding.uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   uploadData(RecipeName,RecipeDescription,RecipeDirection,String.valueOf(imageUri));
            }
        });

    }

    private void uploadData(String recipeName, String recipeDescription, String recipeDirection, String uri) {

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePathName = "RecipeImage"+ "recipe_" + timeStamp;
        if(imageUri != null){
            StorageReference ref = FirebaseStorage.getInstance().getReference(filePathName);
            ref.putFile(Uri.parse(uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ////image upload in firebase storage , no get its url////
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());

                    String downloadUri = uriTask.getResult().toString();
                    if(uriTask.isSuccessful()){
                        ///uri upload to database///

                        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Recipe");



                    }
                }
            });
        }


    }


    public void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,Image_RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == Image_RequestCode){
                imageUri = Objects.requireNonNull(data).getData();
                stepTwoBinding.RecipeCoverImage.setImageURI(imageUri);
            }
        }
    }
}