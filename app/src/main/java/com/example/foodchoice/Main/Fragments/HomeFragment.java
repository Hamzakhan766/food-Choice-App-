package com.example.foodchoice.Main.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodchoice.AdapterClasses.CategoryAdapter;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    DatabaseReference database;
    RecyclerView catNameRecycler;
    FrCategoryAdapter frCategoryAdapter;
    ArrayList<CategoryModel> categoryModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        ///category Name recycler view/////
        catNameRecycler = view.findViewById(R.id.catNameRecycler);
        catNameRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        catNameRecycler.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance().getReference("Categories");
        categoryModelArrayList = new ArrayList<>();
        frCategoryAdapter = new FrCategoryAdapter(getContext(),categoryModelArrayList);
        catNameRecycler.setAdapter(frCategoryAdapter);

        ///Revoke child value of category node////
        database.orderByChild("categoryName").addValueEventListener(new ValueEventListener() {
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

        return view;

    }



}