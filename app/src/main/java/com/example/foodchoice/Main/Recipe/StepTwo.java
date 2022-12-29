package com.example.foodchoice.Main.Recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.AccountCredentials.SignIn;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.databinding.ActivityStepTwoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    DatabaseReference reference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    ArrayList<Integer> cat_id;
    ArrayList<String> cat_Name;
    ArrayList<String> ingredients;
    String catID;

    @Override
    protected void onStart() {
        super.onStart();
        if(user == null){
            startActivity(new Intent(this, SignIn.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        stepTwoBinding = ActivityStepTwoBinding.inflate(getLayoutInflater());
        setContentView(stepTwoBinding.getRoot());
        Intent intent=getIntent();
        ingredients= new ArrayList<>();
        ingredients=intent.getStringArrayListExtra("ingredients");

        Intent formAct = getIntent();
        String RecipeName = formAct.getStringExtra("RecipeName");
        String RecipeDescription = formAct.getStringExtra("RecipeDescription");
        String RecipeDirection = formAct.getStringExtra("RecipeDirection");

//         //userInfo for post recipe////
//        userInfo = FirebaseDatabase.getInstance().getReference("Users");
//        Query query = userInfo.orderByChild("user_id").equalTo(uid);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    name = "" + ds.child("user_UserName").getValue();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //////set spinner adapter fpr category//////////
        reference = FirebaseDatabase.getInstance().getReference("Categories");
        cat_id = new ArrayList<Integer>();
        cat_Name = new ArrayList<String>();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cat_Name);
        catID= String.valueOf(stepTwoBinding.catList.getSelectedItemId());

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



        stepTwoBinding.RecipeIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepTwo.this,IngredientsSelected.class));
                Animatoo.INSTANCE.animateFade(StepTwo.this);
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
                uploadData("RecipeName","RecipeDescription","RecipeDirection",String.valueOf(imageUri),user.getUid());
            }
        });

    }

    private void uploadData(String recipeName, String recipeDescription, String recipeDirection, String uri,String uid) {

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
                    ///uri upload to database///
                    if(uriTask.isSuccessful()){
                       ////recipe uploaded in realtime database///
                        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Recipe");
                        String key =recipeRef.push().getKey();
                        RecipeModel model=new RecipeModel(key,recipeName,recipeDirection,recipeDescription,uid,downloadUri,catID,"abc.mp4");
                        recipeRef.child(key).setValue(model)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            recipeRef.child(key).child("Ingredients").setValue(ingredients)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(StepTwo.this, "Recipe Added Successfull...", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(StepTwo.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                });



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