package com.example.foodchoice.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.AccountCredentials.WelcomeScreen;
import com.example.foodchoice.AdapterClasses.sliderAdapter;
import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivityOnBoardingBinding;

public class onBoarding extends AppCompatActivity {
   ActivityOnBoardingBinding activityOnBoardingBinding;
   sliderAdapter adapter;
   Animation animation;
   int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOnBoardingBinding  = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(activityOnBoardingBinding.getRoot());

        adapter = new sliderAdapter(this);
        activityOnBoardingBinding.slider.setAdapter(adapter);
        activityOnBoardingBinding.dotsIndicator.attachTo(activityOnBoardingBinding.slider);

        activityOnBoardingBinding.skipBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), WelcomeScreen.class));
            Animatoo.INSTANCE.animateFade(onBoarding.this);
            finish();
        });
        activityOnBoardingBinding.nextBtn.setOnClickListener(v -> activityOnBoardingBinding.slider.setCurrentItem(currentPosition + 1));
        activityOnBoardingBinding.getStartedBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), WelcomeScreen.class));
            Animatoo.INSTANCE.animateFade(onBoarding.this);
            finish();
        });

        activityOnBoardingBinding.slider.addOnPageChangeListener(changeListener);

    }

    ///ViewPage listener to set sliderAdapter///
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
            if (position == 0) {
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.INVISIBLE);
                activityOnBoardingBinding.skipBtn.setVisibility(View.VISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.VISIBLE);
            } else if (position == 1) {
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.INVISIBLE);
                activityOnBoardingBinding.skipBtn.setVisibility(View.VISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.VISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(onBoarding.this, R.anim.onboarding_button_anim);
                activityOnBoardingBinding.getStartedBtn.setAnimation(animation);
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.VISIBLE);
                activityOnBoardingBinding.skipBtn.setVisibility(View.INVISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };

}