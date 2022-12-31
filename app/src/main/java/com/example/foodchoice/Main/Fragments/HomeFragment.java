package com.example.foodchoice.Main.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodchoice.AdapterClasses.CategoryAdapter;
import com.example.foodchoice.AdapterClasses.RecipeAdapter;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.Main.Categories.CategoryIndex;
import com.example.foodchoice.Main.Recipe.RecipeIndex;
import com.example.foodchoice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    DatabaseReference database,recipeRef;
    RecyclerView catNameRecycler,recipeRecycler,specificRecipeWithCategory;
    Button cat,trend,recipe;
    FrCategoryAdapter frCategoryAdapter;
    RecipeAdapter recipeAdapter;
    frRecipeAdapter frRecipeAdapter;
    ArrayList<CategoryModel> categoryModelArrayList;
    ArrayList<RecipeModel> recipeModelArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);


        /////buttons////
        cat = view.findViewById(R.id.seeAllCategories);
        trend = view.findViewById(R.id.seeAllTrend);
        recipe = view.findViewById(R.id.seeAllRecipe);
        cat.setOnClickListener(v -> startActivity(new Intent(getContext(), CategoryIndex.class)));
        trend.setOnClickListener(v -> startActivity(new Intent(getContext(), RecipeIndex.class)));
        recipe.setOnClickListener(v -> startActivity(new Intent(getContext(),RecipeIndex.class)));

        ///category Name recycler view/////
        catNameRecycler = view.findViewById(R.id.catNameRecycler);
        catNameRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        catNameRecycler.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance().getReference("Categories");
        categoryModelArrayList = new ArrayList<>();
        frCategoryAdapter = new FrCategoryAdapter(getContext(),categoryModelArrayList);
        catNameRecycler.setAdapter(frCategoryAdapter);
        ///Revoke child value of category node////
        database.orderByChild("categoryName").limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    CategoryModel categoryModel = ds.getValue(CategoryModel.class);
                    categoryModelArrayList.add(categoryModel);
                }
                frCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //////////trending Recipe//////////
        specificRecipeWithCategory = view.findViewById(R.id.specificRecipeWithCategory);
        specificRecipeWithCategory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        specificRecipeWithCategory.setHasFixedSize(true);
        recipeRef = FirebaseDatabase.getInstance().getReference("Recipe");
        recipeModelArrayList = new ArrayList<>();
        frRecipeAdapter = new frRecipeAdapter(getContext(),recipeModelArrayList);
        specificRecipeWithCategory.setAdapter(frRecipeAdapter);
        recipeRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        RecipeModel recipeModel = dataSnapshot.getValue(RecipeModel.class);
                        recipeModelArrayList.add(recipeModel);
                    }
                    frRecipeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ///////////Recommended Recipe view/////////
        recipeRecycler = view.findViewById(R.id.recipeRecycler);
        recipeRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recipeRecycler.setHasFixedSize(true);
        recipeRef = FirebaseDatabase.getInstance().getReference("Recipe");
        recipeModelArrayList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getContext(),recipeModelArrayList);
        recipeRecycler.setAdapter(recipeAdapter);
        recipeRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RecipeModel recipeModel = ds.getValue(RecipeModel.class);
                        recipeModelArrayList.add(recipeModel);
                    }
                    recipeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;

    }



}