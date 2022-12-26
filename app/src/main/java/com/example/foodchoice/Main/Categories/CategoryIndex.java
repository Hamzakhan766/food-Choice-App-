package com.example.foodchoice.Main.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.example.foodchoice.AdapterClasses.CategoryAdapter;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.databinding.ActivityCategoryIndexBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryIndex extends AppCompatActivity {
   ActivityCategoryIndexBinding categoryIndexBinding;
   CategoryAdapter categoryAdapter;
   DatabaseReference reference;
   ArrayList<CategoryModel> categoryModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        categoryIndexBinding = ActivityCategoryIndexBinding.inflate(getLayoutInflater());
        setContentView(categoryIndexBinding.getRoot());


        categoryIndexBinding.CategoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Categories");
        categoryIndexBinding.CategoryIndex.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        categoryIndexBinding.CategoryIndex.setHasFixedSize(true);

        categoryModelArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categoryModelArrayList);
        categoryIndexBinding.CategoryIndex.setAdapter(categoryAdapter);

        reference.orderByChild("categoryName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    CategoryModel cm = ds.getValue(CategoryModel.class);
                    categoryModelArrayList.add(cm);

                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        categoryIndexBinding.CategorySearch.addTextChangedListener(new TextWatcher() {
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

        ArrayList<CategoryModel> filterCategory = new ArrayList<>();

        for(CategoryModel item : categoryModelArrayList){
            if(item.getCategoryName().toLowerCase().contains(Text.toLowerCase())){
                filterCategory.add(item);
            }
        }
        categoryAdapter.FilterList(filterCategory);

    }
}