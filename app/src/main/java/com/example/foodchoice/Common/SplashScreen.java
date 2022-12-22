package com.example.foodchoice.Common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.AccountCredentials.WelcomeScreen;
import com.example.foodchoice.HelperClasses.UserSession;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIMER = 6000;
    SharedPreferences onBoardingScreen;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        UserSession userSession = new UserSession(SplashScreen.this);


       ////splash screen handler////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ///creating onBoarding screen shared preferences////
                onBoardingScreen = getSharedPreferences("onBoardScreen",MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("isFirstTime",true);
                ///checking onBoarding screen is first time  view///
                if (isFirstTime){
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("isFirstTime",false);
                    editor.commit();
                    ///Now show onBoarding screen///
                    startActivity(new Intent(SplashScreen.this,onBoarding.class));
                    Animatoo.INSTANCE.animateFade(SplashScreen.this);
                    finish();
                }
                else if(userSession.checkUserLogin()){
                    startActivity(new Intent(SplashScreen.this, MainDashboard.class));
                    Animatoo.INSTANCE.animateFade(SplashScreen.this);
                    finish();
                }

                else {
                    startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
                    Animatoo.INSTANCE.animateFade(SplashScreen.this);
                    finish();
                }
            }
        },SPLASH_TIMER);

    }
}