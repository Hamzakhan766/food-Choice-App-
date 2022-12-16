package com.example.foodchoice.AccountCredentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.HelperClasses.UserSession;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.R;
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
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding activitySignUpBinding;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());


        ////already have account///
        activitySignUpBinding.alreadyAcc.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),SignIn.class));
            Animatoo.INSTANCE.animateSlideRight(SignUp.this);
        });
        ///backPress////
        activitySignUpBinding.backPressed.setOnClickListener(v -> finish());


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

        if(!validateFullName() | !validateUSerName() | !validateEmail() | !validatePhone() | !validatePassword()){
            return;
        }
        else {
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
                        dbRef.child(firebaseUser.getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //////or debugging purpose///////
                                if(task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "User Register Successfully ", Toast.LENGTH_SHORT).show();
                                    ////////Creating Session/////////
                                    UserSession userSession = new UserSession(SignUp.this);
                                    userSession.loginRegisterSession(FullName, UserName, Email, Phone, Password);

                                    /////redirecting user to main dashboard///
                                    Intent intent = new Intent(SignUp.this, MainDashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    Animatoo.INSTANCE.animateFade(SignUp.this);
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


    //////check validation////////
    public boolean validateFullName(){
         String value = Objects.requireNonNull(activitySignUpBinding.userFullName.getEditText()).getText().toString().trim();
         if (value.isEmpty()){
             activitySignUpBinding.userFullName.setError("Field cannot be empty");
             return  false;
         }else
             activitySignUpBinding.userFullName.setError(null);
             activitySignUpBinding.userFullName.setErrorEnabled(false);
             return true;
    }
    public boolean validateUSerName(){
        String value = Objects.requireNonNull(activitySignUpBinding.userName.getEditText()).getText().toString().trim();
//        String checkSpaces = "(?=\\\\S+$)";
        if (value.isEmpty()){
             activitySignUpBinding.userName.setError("Field cannot be empty");
             return false;
        }else if (value.length() >20 ){
             activitySignUpBinding.userName.setError("username is too large");
             return false;
        }else
            activitySignUpBinding.userName.setError(null);
            activitySignUpBinding.userName.setErrorEnabled(false);
        return true;
    }
    public boolean validateEmail(){
        String value = Objects.requireNonNull(activitySignUpBinding.userEmail.getEditText()).getText().toString();
        if(value.isEmpty()){
            activitySignUpBinding.userEmail.setError("Field cannot be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
               activitySignUpBinding.userEmail.setError("please enter valid email");
               return false;
         }else
             activitySignUpBinding.userEmail.setError(null);
             activitySignUpBinding.userEmail.setErrorEnabled(false);
             return true;
    }
    public  boolean validatePhone(){
        String value = Objects.requireNonNull(activitySignUpBinding.UserPhone.getEditText()).getText().toString().trim();
        if(value.isEmpty()){
            activitySignUpBinding.UserPhone.setError("field cannot be empty");
            return false;
        }
        else if(value.length() != 11){
            activitySignUpBinding.UserPhone.setError("Phone no should be 11");
            return false;
        }
        else
            activitySignUpBinding.UserPhone.setError(null);
            activitySignUpBinding.UserPhone.setErrorEnabled(false);
            return true;
    }
    public boolean validatePassword(){
        String value = Objects.requireNonNull(activitySignUpBinding.userPassword.getEditText()).getText().toString().trim();
        if(value.isEmpty()){
           activitySignUpBinding.userPassword.setError("Field cannot be empty");
           return false;
        }else if(!PASSWORD_PATTERN.matcher(value).matches()){
            activitySignUpBinding.userPassword.setError("Password is too weak");
            return false;
        }else
            activitySignUpBinding.userPassword.setError(null);
            activitySignUpBinding.userPassword.setErrorEnabled(false);
            return true;
    }

}