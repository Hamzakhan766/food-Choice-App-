package com.example.foodchoice.UserCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.foodchoice.databinding.ActivityUserProfileBinding;

public class UserProfile extends AppCompatActivity {
   ActivityUserProfileBinding activityUserProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUserProfileBinding.getRoot());
    }
}