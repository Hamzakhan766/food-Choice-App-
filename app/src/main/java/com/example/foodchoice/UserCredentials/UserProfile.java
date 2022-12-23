package com.example.foodchoice.UserCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.databinding.ActivityUserProfileBinding;

public class UserProfile extends AppCompatActivity {
   ActivityUserProfileBinding activityUserProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUserProfileBinding.getRoot());


        activityUserProfileBinding.updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,updateUserProfile.class));
            }
        });

        activityUserProfileBinding.backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, MainDashboard.class));
                finish();
            }
        });




        
    }
}