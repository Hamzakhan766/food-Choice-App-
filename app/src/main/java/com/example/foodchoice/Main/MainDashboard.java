package com.example.foodchoice.Main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.foodchoice.AccountCredentials.SignIn;
import com.example.foodchoice.AccountCredentials.SignUp;
import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.Main.Categories.CategoryIndex;
import com.example.foodchoice.Main.Fragments.CategoryFragment;
import com.example.foodchoice.Main.Fragments.GroceryFragment;
import com.example.foodchoice.Main.Fragments.HomeFragment;
import com.example.foodchoice.Main.Grocery.GroceryIndex;
import com.example.foodchoice.Main.MealPlanner.MealPlanner;
import com.example.foodchoice.Main.Recipe.RecipeIndex;
import com.example.foodchoice.R;
import com.example.foodchoice.UserCredentials.UserProfile;
import com.example.foodchoice.databinding.ActivityMainDashboardBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityMainDashboardBinding activityMainDashboardBinding;
    static final float END_SCALE = 0.7f;
    SharedPreferences guestPreferences;
    FirebaseAuth currentAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMainDashboardBinding = ActivityMainDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityMainDashboardBinding.getRoot());


        guestPreferences = getSharedPreferences("GuestLogin",MODE_PRIVATE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference currentUserDetails = FirebaseDatabase.getInstance().getReference("Users");

        if(user != null){
            activityMainDashboardBinding.userDashboardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainDashboard.this, UserProfile.class));
                }
            });
            currentUserDetails.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        UserClass userModel = snapshot.getValue(UserClass.class);
                        String userName = userModel.getUser_UserName();
                        activityMainDashboardBinding.userDashboardName.setText(String.format("Hi, %s",userName));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            if(guestPreferences.getBoolean("GUEST_LOGIN",false)){
                activityMainDashboardBinding.userDashboardImage.setOnClickListener(v -> customDialogBox());
                String guestname = guestPreferences.getString("USERNAME","Guest");
                activityMainDashboardBinding.userDashboardName.setText(String.format("Hi, %s",guestname));
                Menu logoutBtn = activityMainDashboardBinding.drawerNav.getMenu();
                logoutBtn.findItem(R.id.nav_logout).setVisible(false);
            }

        }




        ////by default home home menu is selected in bottom navigation///
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentManage, new HomeFragment()).commit();
        activityMainDashboardBinding.bottomNavigation.setItemSelected(R.id.bottom_nav_home, true);
        bottomNavigation();

        ////declaration of drawer navigation function//////
        drawerNavigation();
    }

    ///////bottom navigation with fragments///////
    public void bottomNavigation() {
        activityMainDashboardBinding.bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {
                ////clear stacks when homeFragment clicked ///
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                switch (i) {
                    case R.id.bottom_nav_home:
                        Fragment Home = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().add(new HomeFragment(), "HomeFragment").addToBackStack("HomeFragment").replace(R.id.FragmentManage, Home).commit();
                        break;

                    case R.id.bottom_nav_categories:
                        Fragment Category = new CategoryFragment();
                        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.FragmentManage, Category).commit();
                        break;

                    case R.id.bottom_nav_grocery:
                        Fragment Grocery = new GroceryFragment();
                        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.FragmentManage, Grocery).commit();
                        break;
                }


            }
        });
    }


    /////drawer navigation///
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

    /////Navigation drawer open / close with animation ////
    private void animateNavigateDrawer() {
        activityMainDashboardBinding.drawerMenu.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                ///Scale the View based on current slide offset////
                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaleOffset;

                activityMainDashboardBinding.Content.setScaleX(offsetScale);
                activityMainDashboardBinding.Content.setScaleY(offsetScale);

                ////Translate the View, Mathematics for scaled width////
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = activityMainDashboardBinding.Content.getWidth() * diffScaleOffset;
                final float xTranslation = xOffset - xOffsetDiff;

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


    //////handle back pressed  ////////
    @Override
    public void onBackPressed() {
        if (activityMainDashboardBinding.drawerMenu.isDrawerVisible(GravityCompat.START)) {
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    ////handle menu items clicked in drawer navigation////
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ///when item clicked change activity or something else///

        if(item.getItemId() == R.id.nav_all_categories){
            startActivity(new Intent(MainDashboard.this, CategoryIndex.class));
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }

        if(item.getItemId() == R.id.nav_grocery){
            startActivity(new Intent(MainDashboard.this, GroceryIndex.class));
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }

        if(item.getItemId() == R.id.nav_recipe){
            startActivity(new Intent(MainDashboard.this, RecipeIndex.class));
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }

        if(item.getItemId() == R.id.Meal_Planner){
            startActivity(new Intent(MainDashboard.this, MealPlanner.class));
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }

        if (item.getItemId() == R.id.nav_login) {
            startActivity(new Intent(MainDashboard.this, SignIn.class));
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }
        if (item.getItemId() == R.id.nav_signUp) {
            startActivity(new Intent(MainDashboard.this, SignUp.class));
            activityMainDashboardBinding.drawerMenu.closeDrawer(GravityCompat.START);
        }

        if(item.getItemId() == R.id.nav_logout){

        }

        return false;
    }


    public void customDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainDashboard.this);
        View view = LayoutInflater.from(MainDashboard.this).inflate(R.layout.guest_mode, null);
        builder.setView(view);

        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        view.findViewById(R.id.btnOkay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}