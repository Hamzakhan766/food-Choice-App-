package com.example.foodchoice.UserCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.foodchoice.HelperClasses.UserSession;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.databinding.ActivityUserProfileBinding;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {
   ActivityUserProfileBinding activityUserProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUserProfileBinding.getRoot());

        UserSession userSession = new UserSession(this);
        HashMap<String , String> userDetails = userSession.getUserDetailFromSession();
        String userName = userDetails.get(UserSession.KEY_USERNAME);
        activityUserProfileBinding.userName.setText(userName);



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