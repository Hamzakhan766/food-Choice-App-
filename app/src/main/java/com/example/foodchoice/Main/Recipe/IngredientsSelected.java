package com.example.foodchoice.Main.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.foodchoice.HelperClasses.GroceryModel;
import com.example.foodchoice.databinding.ActivityIngrediantsSelectedBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientsSelected extends AppCompatActivity implements ingredientsQuantityListner {
     ActivityIngrediantsSelectedBinding binding;
     Ingredients_Adapter adapter;
    ArrayList<String> ingredients = new ArrayList<>();
     ArrayList<GroceryModel> groceryModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Ingredients_Adapter(groceryModelArrayList,this,this);
        binding = ActivityIngrediantsSelectedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getingredients();

        binding.btnadd.setOnClickListener(view -> {
            Intent intent = new Intent(this,AddRecipe.class);
            intent.putExtra("ingredients",ingredients);
            startActivity(intent);
        });
        binding.ingredients.setHasFixedSize(true);
        binding.ingredients.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.ingredients.setAdapter(adapter);
    }

    private void getingredients() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groceries");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()){
                    GroceryModel model = data.getValue(GroceryModel.class);
                    groceryModelArrayList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(IngredientsSelected.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void ingredientsQuantityChange(ArrayList<String> arrayList) {
        Toast.makeText(this, ""+arrayList.size(), Toast.LENGTH_SHORT).show();
        ingredients=arrayList;
    }
}