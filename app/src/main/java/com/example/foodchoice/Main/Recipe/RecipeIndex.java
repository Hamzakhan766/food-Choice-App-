package com.example.foodchoice.Main.Recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodchoice.AdapterClasses.RecipeAdapter;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.databinding.ActivityRecipeIndexBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeIndex extends AppCompatActivity {
    ActivityRecipeIndexBinding recipeIndexBinding;
    RecipeAdapter recipeAdapter;
    ArrayList<RecipeModel> recipeModelArrayList = new ArrayList<>();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recipeIndexBinding = ActivityRecipeIndexBinding.inflate(getLayoutInflater());
        setContentView(recipeIndexBinding.getRoot());


        reference = FirebaseDatabase.getInstance().getReference("Recipe");
        recipeIndexBinding.RecipeIndex.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recipeIndexBinding.RecipeIndex.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this, recipeModelArrayList);
        recipeIndexBinding.RecipeIndex.setAdapter(recipeAdapter);

        reference.addValueEventListener(new ValueEventListener() {

            String catName = "";
            String uName = "";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        RecipeModel recipeModel = ds.getValue(RecipeModel.class);

                        String RecipeNameCard = ds.child("recipeName").getValue().toString();
                        String RecipeImage = ds.child("recipeImageUrl").getValue().toString();
                        String RecipeId = ds.getKey().toString();
                        String RecipeServing = ds.child("recipeServing").getValue().toString();
                        String recipeDescriptionCard = ds.child("recipeDescription").getValue().toString();
                      String c_id =   ds.child("recipeCategoryID").getValue().toString();
                        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("Categories");
                        categoryRef.child("categoryId").equalTo(c_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    String key = dataSnapshot.getKey().toString();
//                                    if (key.equals(c_id)) {
//                                        catName = dataSnapshot.child("categoryName").getValue().toString();
                                        Log.d("cat name", snapshot.toString());
//                                    }
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                                userRef.addValueEventListener(new ValueEventListener() {
                                                                  @Override
                                                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                      for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                                          String uId = ds.child("userID").getValue().toString();
                                                                          if (dataSnapshot.getKey().equals(ds.child("userID").getValue().toString())) {
                                                                              uName = dataSnapshot.child("user_UserName").getValue().toString();
                                                                              RecipeModel recipeModel = new RecipeModel(RecipeId, RecipeNameCard, recipeDescriptionCard, RecipeServing, uName, RecipeImage, catName);
                                                                              recipeModelArrayList.add(recipeModel);
                                                                              Log.d("my record", catName);
                                                                          }
                                                                      }

                                                                      recipeAdapter = new RecipeAdapter(RecipeIndex.this, recipeModelArrayList);
                                                                      recipeIndexBinding.RecipeIndex.setAdapter(recipeAdapter);
                                                                  }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }


                    recipeAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(RecipeIndex.this, "something went wrong to fetch recipes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecipeIndex.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        recipeIndexBinding.RecipeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String Text) {

        ArrayList<RecipeModel> filterRecipe = new ArrayList<>();
        for (RecipeModel item : recipeModelArrayList) {
            if (item.getRecipeName().toLowerCase().contains(Text.toLowerCase())) {
                filterRecipe.add(item);
            }
            recipeAdapter.FilterList(filterRecipe);
        }

    }
}