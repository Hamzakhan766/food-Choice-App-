package com.example.foodchoice.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.AccountCredentials.SignIn;
import com.example.foodchoice.AccountCredentials.SignUp;
import com.example.foodchoice.HelperClasses.UserSession;
import com.example.foodchoice.Main.Fragments.CategoryFragment;
import com.example.foodchoice.Main.Fragments.HomeFragment;
import com.example.foodchoice.Main.Fragments.GroceryFragment;
import com.example.foodchoice.R;
import com.example.foodchoice.UserCredentials.UserProfile;
import com.example.foodchoice.databinding.ActivityMainDashboardBinding;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;
import java.util.Objects;

public class MainDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityMainDashboardBinding activityMainDashboardBinding;
    static final float END_SCALE = 0.7f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMainDashboardBinding = ActivityMainDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityMainDashboardBinding.getRoot());


        activityMainDashboardBinding.userDashboardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDashboard.this, UserProfile.class));
                Animatoo.INSTANCE.animateSlideUp(MainDashboard.this);
            }
        });



        ///User session ///
        UserSession userSession = new UserSession(this);
        HashMap<String, String> getUserDetails = userSession.getUserDetailFromSession();
        String userName = getUserDetails.get(UserSession.KEY_USERNAME);
        activityMainDashboardBinding.userDashboardName.setText(String.format("Hi, %s", userName));

        ///bottom Navigation function////
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentManage,new HomeFragment()).commit();
        activityMainDashboardBinding.bottomNavigation.setItemSelected(R.id.bottom_nav_home,true);
        bottomNavigation();

        ////drawer navigation//////
        drawerNavigation();
    }

    public void bottomNavigation(){
        activityMainDashboardBinding.bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.bottom_nav_categories:
                        fragment = new CategoryFragment();
                        break;

                    case R.id.bottom_nav_profile:
                        fragment = new GroceryFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentManage, Objects.requireNonNull(fragment)).commit();
            }
        });
    }


    private void drawerNavigation() {
        activityMainDashboardBinding.drawerNav.bringToFront();
        activityMainDashboardBinding.drawerNav.setNavigationItemSelectedListener(this);
        activityMainDashboardBinding.drawerNav.setCheckedItem(R.id.nav_home);

        activityMainDashboardBinding.hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityMainDashboardBinding.drawerMenu.isDrawerVisible(GravityCompat.START)) {
                    activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
             } else {
                    activityMainDashboardBinding.drawerMenu.openDrawer(GravityCompat.START);
              }
            }
        });

        animateNavigateDrawer();

    }

    private void animateNavigateDrawer() {
        activityMainDashboardBinding.drawerMenu.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                ///Scale the View based on current slide offset////
                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale =  1 - diffScaleOffset;

                activityMainDashboardBinding.Content.setScaleX(offsetScale);
                activityMainDashboardBinding.Content.setScaleY(offsetScale);

                ////Translate the View, Mathematics for scaled width////
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = activityMainDashboardBinding.Content.getWidth() * diffScaleOffset;
                final  float xTranslation = xOffset - xOffsetDiff;

                activityMainDashboardBinding.Content.setTranslationX(xTranslation);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(activityMainDashboardBinding.drawerMenu.isDrawerVisible(GravityCompat.START)){
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragmentDrawer;
        ///when item clicked change activity or something else///
        switch (item.getItemId()){

            case  R.id.nav_all_categories:
                 fragmentDrawer = new CategoryFragment();
                 getSupportFragmentManager().beginTransaction().replace(R.id.FragmentManage,fragmentDrawer).commit();
                 activityMainDashboardBinding.userDashboardName.setText("Categories");
                 activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
                 activityMainDashboardBinding.bottomNavigation.setVisibility(View.INVISIBLE);
                 break;


            case R.id.nav_login:
                startActivity(new Intent(MainDashboard.this, SignIn.class));
                activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_signUp:
                startActivity(new Intent(MainDashboard.this, SignUp.class));
                activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
                break;
        }
        return false;
    }
}