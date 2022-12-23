package com.example.foodchoice.UserCredentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.databinding.ActivityUpdateUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateUserProfile extends AppCompatActivity {
    ActivityUpdateUserProfileBinding userProfileBinding;
    FirebaseAuth userAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userProfileBinding = ActivityUpdateUserProfileBinding.inflate(getLayoutInflater());
        setContentView(userProfileBinding.getRoot());


        userProfileBinding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        showProfile();

    }

    private void showProfile() {

        userAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = userAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserClass  readUser = snapshot.getValue(UserClass.class);
                if(readUser != null){
                    String fullName = readUser.getUser_fullName();
                    String userName = readUser.getUser_UserName();
                    String userEmail = readUser.getUser_UseEmail();
                    String userPhone = readUser.getUser_UserPhone();
                    String userPassword = readUser.getUser_UserPassword();

                userProfileBinding.updateFullName.getEditText().setText(fullName);
                userProfileBinding.updateUserName.getEditText().setText(userName);
                userProfileBinding.updateEmail.getEditText().setText(userEmail);
                userProfileBinding.updatePhone.getEditText().setText(userPhone);
                userProfileBinding.updatePassword.getEditText().setText(userPassword);

                }
               else {
                    Toast.makeText(updateUserProfile.this, "Empty Result", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}