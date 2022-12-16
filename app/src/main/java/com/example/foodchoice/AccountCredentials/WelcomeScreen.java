package com.example.foodchoice.AccountCredentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.databinding.ActivityWelcomeScreenBinding;

public class WelcomeScreen extends AppCompatActivity {
    ActivityWelcomeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityWelcomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGuest.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
            Animatoo.INSTANCE.animateFade(WelcomeScreen.this);
            finish();
        });
        binding.SignIn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),SignIn.class));
            Animatoo.INSTANCE.animateFade(WelcomeScreen.this);
        });
        binding.SignUp.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),SignUp.class));
            Animatoo.INSTANCE.animateFade(WelcomeScreen.this);
        });
    }
}