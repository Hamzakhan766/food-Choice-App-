package com.example.foodchoice.AccountCredentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding activitySignUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());

        ////////registration///////////
        activitySignUpBinding.SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser();
            }
        });

    }


    ///register Function///
    public void registerUser(){

            ///Hooks///
            String FullName =  Objects.requireNonNull(activitySignUpBinding.userFullName.getEditText()).getText().toString();
            String UserName =  Objects.requireNonNull(activitySignUpBinding.userName.getEditText()).getText().toString();
            String Email  =  Objects.requireNonNull(activitySignUpBinding.userEmail.getEditText()).getText().toString();
            String Phone =  Objects.requireNonNull(activitySignUpBinding.UserPhone.getEditText()).getText().toString();
            String Password =  Objects.requireNonNull(activitySignUpBinding.userPassword.getEditText()).getText().toString();


            ////registration with firebase authentication/////
            FirebaseAuth userAuth = FirebaseAuth.getInstance();
            userAuth.createUserWithEmailAndPassword(Email , Password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = userAuth.getCurrentUser();
                        ///Save user into realtime database /////
                        UserClass userModel = new UserClass(FullName,UserName,Email,Phone,Password);
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
                        dbRef.child(Objects.requireNonNull(firebaseUser).getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //////or debugging purpose///////
                                if(task.isSuccessful()) {


                                    /////redirecting user to main dashboard///
                                    Intent intent = new Intent(SignUp.this, MainDashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, "Failed!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).addOnFailureListener(SignUp.this, e -> Toast.makeText(SignUp.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }



}