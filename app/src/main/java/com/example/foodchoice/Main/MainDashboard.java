package com.example.foodchoice.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.foodchoice.HelperClasses.UserSession;
import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivityMainDashboardBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class MainDashboard extends AppCompatActivity {
    ActivityMainDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /////get username in userSession//////
        UserSession userSession = new UserSession(this);
        HashMap<String,String> userDetails = userSession.getUserDetailFromSession();

        String userName = userDetails.get(UserSession.KEY_USERNAME);
        binding.dynamicUsername.setText("Hi, " + userName);

    }



}