package com.example.foodchoice.Main.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class CategoryFragment extends Fragment {
    RecyclerView rvCategory;
    CategoryAdapter adapter;
    ArrayList<CategoryModel> categoryModelArrayList;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rvCategory = view.findViewById(R.id.CategoryRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        rvCategory.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        rvCategory.setHasFixedSize(true);
        categoryModelArrayList = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(),categoryModelArrayList);
        rvCategory.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   CategoryModel categoryModel = dataSnapshot.getValue(CategoryModel.class);
                   categoryModelArrayList.add(categoryModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}