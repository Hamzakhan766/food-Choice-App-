package com.example.foodchoice.AccountCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivitySignInBinding;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding activitySignInBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(activitySignInBinding.getRoot());

        ////create new account////
        activitySignInBinding.SignCreateAcc.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),SignUp.class));
            Animatoo.INSTANCE.animateSlideRight(SignIn.this);
        });

        ///backPress////
        activitySignInBinding.SignBackPressed.setOnClickListener(v -> finish());
    }
}