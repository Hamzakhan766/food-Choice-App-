package com.example.foodchoice.Main.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityIngrediantsSelectedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        getingredients();
        adapter = new Ingredients_Adapter(groceryModelArrayList,this,this);
        binding.btnadd.setOnClickListener(view -> {
            Intent intent = new Intent(this,AddRecipe.class);
            intent.putExtra("ingredients",ingredients);
            startActivity(intent);
        });


        binding.IngredientsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                  filter(editable.toString());
            }
        });


        binding.ingredients.setHasFixedSize(true);
        binding.ingredients.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.ingredients.setAdapter(adapter);
    }

    private void filter(String text) {
        ArrayList<GroceryModel> filterGrocery = new ArrayList<>();
        for (GroceryModel item : groceryModelArrayList){
            if(item.getGroceryName().toLowerCase().contains(text.toLowerCase())){
                filterGrocery.add(item);
            }
        }
        adapter.filterIngredients(filterGrocery);

    }

    private void getingredients() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("grocery");
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
        Toast.makeText(this, arrayList.size()+"is selected", Toast.LENGTH_SHORT).show();
        ingredients=arrayList;
    }
}