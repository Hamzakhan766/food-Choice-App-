package com.example.foodchoice.Main.Recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.databinding.ActivityAddRecipeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class AddRecipe extends AppCompatActivity {
    ActivityAddRecipeBinding addRecipeBinding;
    ArrayList<String> ingredients;
    private final int Image_RequestCode = 100;
    Uri imageUri = null;
    FirebaseAuth userAuth = FirebaseAuth.getInstance();
    FirebaseUser user = userAuth.getCurrentUser();
    ArrayList<Integer> cat_id;
    ArrayList<String> cat_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addRecipeBinding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(addRecipeBinding.getRoot());

        Intent intent=getIntent();
        ingredients= new ArrayList<>();
        ingredients=intent.getStringArrayListExtra("ingredients");


        DatabaseReference category = FirebaseDatabase.getInstance().getReference("Categories");
        cat_id = new ArrayList<Integer>();
        cat_Name = new ArrayList<String>();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cat_Name);
        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        cat_id.add(Integer.parseInt(String.valueOf(ds.child("categoryId").getValue())));
                        cat_Name.add(String.valueOf(ds.child("categoryName").getValue()));
                    }
                    addRecipeBinding.catList.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(AddRecipe.this, "category does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddRecipe.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        addRecipeBinding.RecipeIngredients.setOnClickListener(v -> {
            startActivity(new Intent(AddRecipe.this,IngredientsSelected.class));
            Animatoo.INSTANCE.animateFade(AddRecipe.this);
        });


       addRecipeBinding.uploadCoverImage.setOnClickListener(v -> {
           pickFromGallery();
           addRecipeBinding.uploadCoverImage.setVisibility(View.GONE);
       });
       
       addRecipeBinding.uploadRecipe.setOnClickListener(v -> uploadRecipe());

    }

    private void uploadRecipe() {

        if(imageUri != null){
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Recipe_Images").child(System.currentTimeMillis()+"");
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DatabaseReference recipe = FirebaseDatabase.getInstance().getReference("Recipe");
                            String key = recipe.push().getKey();
                            RecipeModel recipeModel = new RecipeModel();
                            recipeModel.setRecipeImageUrl(uri.toString());

                            recipeModel.setRecipeID(key);
                            recipeModel.setRecipeName(Objects.requireNonNull(addRecipeBinding.RecipeName.getText()).toString().trim());
                            recipeModel.setRecipeDirection(Objects.requireNonNull(addRecipeBinding.RecipeDirection.getText()).toString().trim());
                            recipeModel.setRecipeDescription(Objects.requireNonNull(addRecipeBinding.RecipeDescription.getText()).toString().trim());
                            recipeModel.setRecipeTiming(Objects.requireNonNull(addRecipeBinding.RecipeTiming.getText()).toString().trim());
                            recipeModel.setRecipeServing(Objects.requireNonNull(addRecipeBinding.RecipeServing.getText()).toString().trim());
                            recipeModel.setUserID(user.getUid());

                            recipe.child(Objects.requireNonNull(key)).setValue(recipeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                                    recipe.child(key).child("Ingredients").setValue(ingredients).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    });
                                           }else{
                                               Toast.makeText(AddRecipe.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                           }
                                }
                            });

                        }
                    });
                }
            });
        }else
            Toast.makeText(this, "Image is getting null.......", Toast.LENGTH_SHORT).show();


    }

    private void pickFromGallery() {
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
               addRecipeBinding.RecipeCoverImage.setImageURI(imageUri);
            }
        }

    }
}