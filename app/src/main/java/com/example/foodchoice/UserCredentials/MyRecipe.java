package com.example.foodchoice.UserCredentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.foodchoice.AdapterClasses.RecipeAdapter;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.databinding.ActivityMyRecipeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRecipe extends AppCompatActivity {
   ActivityMyRecipeBinding recipeBinding;
   RecipeAdapter recipeAdapter;
    ArrayList<RecipeModel> recipeModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recipeBinding = ActivityMyRecipeBinding.inflate(getLayoutInflater());
        setContentView(recipeBinding.getRoot());


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        recipeBinding.backInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipe");
        recipeBinding.myRecipeIndex.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false));
        recipeBinding.myRecipeIndex.setHasFixedSize(true);


        recipeAdapter = new RecipeAdapter(this,recipeModelArrayList);
        recipeBinding.myRecipeIndex.setAdapter(recipeAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RecipeModel recipeModel = snapshot.getValue(RecipeModel.class);
                if(recipeModel.getUserID().equals(user.getUid())){
                    recipeModelArrayList.add(recipeModel);
                }else
                    Toast.makeText(MyRecipe.this, "No recipe in your list", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyRecipe.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}