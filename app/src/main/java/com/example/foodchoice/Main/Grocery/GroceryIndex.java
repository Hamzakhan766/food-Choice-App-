package com.example.foodchoice.Main.Grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;

import com.example.foodchoice.AdapterClasses.GroceryAdapter;
import com.example.foodchoice.HelperClasses.GroceryModel;
import com.example.foodchoice.databinding.ActivityGroceryIndexBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class GroceryIndex extends AppCompatActivity {
   ActivityGroceryIndexBinding groceryIndexBinding;
   ArrayList<GroceryModel> groceryModelArrayList;
   GroceryAdapter groceryAdapter;
   DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        groceryIndexBinding = ActivityGroceryIndexBinding.inflate(getLayoutInflater());
        setContentView(groceryIndexBinding.getRoot());



        groceryIndexBinding.groceryBack.setOnClickListener(v -> onBackPressed());

        reference = FirebaseDatabase.getInstance().getReference("Grocery");
        groceryIndexBinding.GroceryIndex.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        groceryIndexBinding.GroceryIndex.setHasFixedSize(true);

        groceryModelArrayList = new ArrayList<>();
        groceryAdapter = new GroceryAdapter(this, groceryModelArrayList);
        groceryIndexBinding.GroceryIndex.setAdapter(groceryAdapter);

        reference.orderByChild("groceryName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot dsg : snapshot.getChildren()){
                      GroceryModel gm = dsg.getValue(GroceryModel.class);
                      groceryModelArrayList.add(gm);
                 }
                 groceryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        groceryIndexBinding.GrocerySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                groceryFilter(s.toString());
            }
        });

    }

    private void groceryFilter(String Text) {
        ArrayList<GroceryModel> groceryFilter = new ArrayList<>();
        for (GroceryModel item : groceryModelArrayList){
            if(item.getGroceryName().toLowerCase().contains(Text.toLowerCase())){
                groceryFilter.add(item);
            }
        }
        groceryAdapter.FilterGrocery(groceryFilter);
    }
}