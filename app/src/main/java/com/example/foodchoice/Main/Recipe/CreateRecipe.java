package com.example.foodchoice.Main.Recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.databinding.ActivityCreateRecipeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class CreateRecipe extends AppCompatActivity {
    ActivityCreateRecipeBinding createRecipeBinding;
    private final int Image_RequestCode = 100;
    private final int Video_RequestCode = 101;
    ArrayList<String> ingredients;
    Uri imageUri = null;
    Uri videoUri = null;
    ProgressDialog dialog;
    MediaController mediaController;
    FirebaseAuth userAuth = FirebaseAuth.getInstance();
    FirebaseUser user = userAuth.getCurrentUser();
    ArrayList<Integer> cat_id;
    ArrayList<String> cat_Name;
    String category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        createRecipeBinding = ActivityCreateRecipeBinding.inflate(getLayoutInflater());
        setContentView(createRecipeBinding.getRoot());


        createRecipeBinding.btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipeBinding.lineDescrip.setVisibility(View.VISIBLE);
                createRecipeBinding.btnDes.setVisibility(View.VISIBLE);
                createRecipeBinding.btnName.setVisibility(View.GONE);
            }
        });
        createRecipeBinding.btnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipeBinding.linearSteps.setVisibility(View.VISIBLE);
                createRecipeBinding.btnStpes.setVisibility(View.VISIBLE);
                createRecipeBinding.btnDes.setVisibility(View.GONE);
            }
        });
        createRecipeBinding.btnStpes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipeBinding.btnStpes.setVisibility(View.GONE);
                createRecipeBinding.linearServing.setVisibility(View.VISIBLE);
                createRecipeBinding.btnServe.setVisibility(View.VISIBLE);
            }
        });
        createRecipeBinding.btnServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipeBinding.btnServe.setVisibility(View.GONE);
                createRecipeBinding.linearCat.setVisibility(View.VISIBLE);
                createRecipeBinding.btnCat.setVisibility(View.VISIBLE);
            }
        });
        createRecipeBinding.btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipeBinding.btnCat.setVisibility(View.GONE);
                createRecipeBinding.linearImage.setVisibility(View.VISIBLE);
                createRecipeBinding.btnImage.setVisibility(View.VISIBLE);
            }
        });
        createRecipeBinding.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipeBinding.btnImage.setVisibility(View.GONE);
                createRecipeBinding.linearVideo.setVisibility(View.VISIBLE);
            }
        });


        /////progress dialog///
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait ....");
        dialog.setCancelable(false);
        dialog.setTitle("Recipe uploading");
        dialog.setCanceledOnTouchOutside(false);


        ///////get all ingredients exist on database/////////
        Intent intent = getIntent();
        ingredients = new ArrayList<>();
        ingredients = intent.getStringArrayListExtra("ingredients");
        createRecipeBinding.RecipeIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateRecipe.this, IngredientsSelected.class));
            }
        });


        ///////pick image from mobile/////////
        createRecipeBinding.uploadCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromMobile();
                createRecipeBinding.uploadCoverImage.setVisibility(View.GONE);
            }
        });


        mediaController = new MediaController(this);
        createRecipeBinding.RecipeVideoUrl.setMediaController(mediaController);
        createRecipeBinding.RecipeVideoUrl.start();
        createRecipeBinding.uploadRecipeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromMobileVideo();
                createRecipeBinding.uploadRecipeVideo.setVisibility(View.GONE);
            }
        });


        createRecipeBinding.UploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe();
            }
        });
        createRecipeBinding.catList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_id =String.valueOf( cat_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatabaseReference category = FirebaseDatabase.getInstance().getReference("Categories");
        cat_id = new ArrayList<Integer>();
        cat_Name = new ArrayList<String>();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cat_Name);

        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        cat_id.add(Integer.parseInt(String.valueOf(ds.child("categoryId").getValue())));
                        cat_Name.add(String.valueOf(ds.child("categoryName").getValue()));
                    }

                    createRecipeBinding.catList.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateRecipe.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadRecipe() {
        dialog.show();

        if (imageUri != null) {

            final StorageReference reference = FirebaseStorage.getInstance().getReference("Recipe_Images").child(System.currentTimeMillis() + "");
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uriImage) {

                            StorageReference reference2 = FirebaseStorage.getInstance().getReference("Recipe_Videos").child(System.currentTimeMillis() + "");
                            reference2.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    reference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uriVideo) {

                                            DatabaseReference recipe = FirebaseDatabase.getInstance().getReference("Recipe");
                                            String key = recipe.push().getKey();
                                            RecipeModel recipeModel = new RecipeModel();

                                            recipeModel.setRecipeName(createRecipeBinding.RecipeName.getText().toString());
                                            recipeModel.setRecipeDescription(createRecipeBinding.RecipeDescription.getText().toString());
                                            recipeModel.setRecipeDirection(createRecipeBinding.RecipeDirection.getText().toString());
                                            recipeModel.setRecipeServing(createRecipeBinding.RecipeServing.getText().toString());
                                            recipeModel.setRecipeImageUrl(uriImage.toString());
                                            recipeModel.setRecipeVideoUrl(uriVideo.toString());
                                            recipeModel.setUserID(user.getUid());
                                            recipeModel.setRecipeCategoryID(category_id);

                                            recipe.child(key).setValue(recipeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        recipe.child(key).child("Ingredients").setValue(ingredients).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                dialog.dismiss();
                                                                startActivity(new Intent(CreateRecipe.this, MainDashboard.class));
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateRecipe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateRecipe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(this, "No image or video is selected", Toast.LENGTH_SHORT).show();
        }


    }

    private void pickFromMobileVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, Video_RequestCode);
    }


    ////pick image///
    private void pickFromMobile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Image_RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Image_RequestCode) {
                imageUri = Objects.requireNonNull(data).getData();
                createRecipeBinding.RecipeCoverImage.setImageURI(imageUri);
            }

            if (requestCode == Video_RequestCode) {
                videoUri = Objects.requireNonNull(data).getData();
                createRecipeBinding.RecipeVideoUrl.setVideoURI(videoUri);
            }

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}