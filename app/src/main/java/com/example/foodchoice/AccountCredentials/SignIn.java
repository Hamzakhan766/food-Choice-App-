package com.example.foodchoice.AccountCredentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.foodchoice.HelperClasses.UserSession;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding activitySignInBinding;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");
    private static final String TAG = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(activitySignInBinding.getRoot());

       activitySignInBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loginUser();
           }
       });

    }

    public  void loginUser(){

        if(!validateEmail() | !validatePassword()){
            return;
        }

        ///Hooks///
        String Email = activitySignInBinding.LoginEmail.getEditText().getText().toString().trim();
        String Password = activitySignInBinding.loginPassword.getEditText().getText().toString().trim();


        FirebaseAuth getAuth = FirebaseAuth.getInstance();
        getAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   /////redirecting user to main dashboard///
                   Intent intent = new Intent(SignIn.this, MainDashboard.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
                   finish();
               }else {
                   try{
                       throw task.getException();
                   }catch (FirebaseAuthInvalidUserException e){
                       activitySignInBinding.LoginEmail.setError("Email address not exits. Kindly , Signup again.");
                       activitySignInBinding.LoginEmail.requestFocus();
                   }
                   catch (FirebaseAuthInvalidCredentialsException e){
                      activitySignInBinding.LoginEmail.setError("Invalid Credentials. Kindly, check and re-enter");
                      activitySignInBinding.LoginEmail.requestFocus();
                   }catch (Exception e){
                       Log.e(TAG,e.getMessage());
                   }
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public boolean validateEmail(){
        String value = Objects.requireNonNull(activitySignInBinding.LoginEmail.getEditText()).getText().toString();
        if(value.isEmpty()){
            activitySignInBinding.LoginEmail.setError("Field cannot be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            activitySignInBinding.LoginEmail.setError("please enter valid email");
            return false;
        }else{
            activitySignInBinding.LoginEmail.setError(null);
            activitySignInBinding.LoginEmail.setErrorEnabled(false);
            return true;}
    }

    public boolean validatePassword(){
        String value = Objects.requireNonNull(activitySignInBinding.loginPassword.getEditText()).getText().toString().trim();
        if(value.isEmpty()){
            activitySignInBinding.loginPassword.setError("Field cannot be empty");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(value).matches()){
            activitySignInBinding.loginPassword.setError("Password is too weak");
            return false;
        }else{
            activitySignInBinding.loginPassword.setError(null);
            activitySignInBinding.loginPassword.setErrorEnabled(false);
            return true;}
    }

}