package com.example.foodchoice.Main.MealPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivityIndexBinding;

public class Index extends AppCompatActivity {
    ActivityIndexBinding indexBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        indexBinding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(indexBinding.getRoot());


        topNavigation();
    }

    public void topNavigation() {

        indexBinding.topNavigation.setOnItemSelectedListener(i -> {

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            switch (i) {
                case R.id.breakFast:
                    Fragment breakFastFragment = new BreakFastFragment();
                    getSupportFragmentManager().beginTransaction().add(new BreakFastFragment(), "BreakFastFragment").addToBackStack("BreakFastFragment").replace(R.id.MealFragmentManage, breakFastFragment).commit();
                    break;

                case R.id.Lunch:
                    Fragment lunch = new LunchFragment();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.MealFragmentManage, lunch).commit();
                    break;

                case R.id.Dinner:
                    Fragment dinner = new DinnerFragment();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.MealFragmentManage, dinner).commit();
                    break;
            }

        });


    }
}