package com.example.foodchoice.Main.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodchoice.AdapterClasses.GroceryAdapter;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.HelperClasses.GroceryModel;
import com.example.foodchoice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroceryFragment extends Fragment {
     RecyclerView rvGrocery;
     GroceryAdapter adapter;
     ArrayList<GroceryModel> groceryModelArrayList;
     DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_grocery, container, false);

        rvGrocery = view.findViewById(R.id.groceryRecycler);
        databaseReference = FirebaseDatabase.getInstance().getReference("Grocery");
        rvGrocery.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvGrocery.setHasFixedSize(true);

        groceryModelArrayList = new ArrayList<>();
        adapter = new GroceryAdapter(getContext(),groceryModelArrayList);
        rvGrocery.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    GroceryModel groceryModel = dataSnapshot.getValue(GroceryModel.class);
                    groceryModelArrayList.add(groceryModel);
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