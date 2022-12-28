package com.example.foodchoice.AccountCredentials;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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


public class SignIn extends AppCompatActivity {
    ActivitySignInBinding activitySignInBinding;
    private static final String TAG = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(activitySignInBinding.getRoot());

        ///////create an account///////
        activitySignInBinding.RegisterHere.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
            Animatoo.INSTANCE.animateSlideRight(SignIn.this);
        });


        ///Login functionalities////
        activitySignInBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    public void loginUser() {
        ///Hooks///
        String Email = Objects.requireNonNull(activitySignInBinding.LoginEmail.getEditText()).getText().toString().trim();
        String Password = Objects.requireNonNull(activitySignInBinding.loginPassword.getEditText()).getText().toString().trim();

        if (Email.isEmpty()) {
            customDialogBox();
        } else if (Password.isEmpty()) {
            customDialogBox();
        } else {

            activitySignInBinding.progressBar.setVisibility(View.VISIBLE);

            ////login with database///
            FirebaseAuth getAuth = FirebaseAuth.getInstance();
            getAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        activitySignInBinding.progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(SignIn.this, MainDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            EmailDialogBox();
                            activitySignInBinding.progressBar.setVisibility(View.GONE);
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            InvalidDialogBox();
                            activitySignInBinding.progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }


    }


    public void customDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
        View view = LayoutInflater.from(SignIn.this).inflate(R.layout.vaildate_empty_field_dialog, null);
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

    @SuppressLint("MissingInflatedId")
    public void EmailDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
        View view = LayoutInflater.from(SignIn.this).inflate(R.layout.email_does_not, null);
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

    @SuppressLint("MissingInflatedId")
    public void InvalidDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
        View view = LayoutInflater.from(SignIn.this).inflate(R.layout.invalid_cred, null);
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