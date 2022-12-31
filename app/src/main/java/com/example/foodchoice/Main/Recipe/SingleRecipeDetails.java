package com.example.foodchoice.Main.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.foodchoice.databinding.ActivitySingleRecipeDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleRecipeDetails extends AppCompatActivity {
    ActivitySingleRecipeDetailsBinding singleRecipeDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        singleRecipeDetailsBinding =ActivitySingleRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(singleRecipeDetailsBinding.getRoot());

        singleRecipeDetailsBinding.SingleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        //////get details form single recipe items//////
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("SingleRecipeImage")).into(singleRecipeDetailsBinding.SingleRecipeImage);
        singleRecipeDetailsBinding.SingleRecipeName.setText(getIntent().getStringExtra("SingleRecipeName"));
        singleRecipeDetailsBinding.SingleRecipeDescription.setText(getIntent().getStringExtra("SingleRecipeDescription"));
        singleRecipeDetailsBinding.SingleRecipeServing.setText(getIntent().getStringExtra("SingleRecipeServing"));
        singleRecipeDetailsBinding.SingleRecipeDirection.setText(getIntent().getStringExtra("SingleRecipeDirection"));

        DatabaseReference getUserName = FirebaseDatabase.getInstance().getReference("Users");
        getUserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                      String userName = dataSnapshot.child("user_UserName").getValue().toString();
                      singleRecipeDetailsBinding.SingleRecipeUserName.setText(userName);
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}