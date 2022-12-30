package com.example.foodchoice.AccountCredentials;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.Main.MainDashboard;
import com.example.foodchoice.R;
import com.example.foodchoice.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
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
                    ".{8,}" +                // at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());


        ////////Custom validation//////
        activitySignUpBinding.userChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  String username = s.toString();
                  if(username.contains("(?=\\S+$)")){
                      activitySignUpBinding.userName.setHelperText("No white spaces allowed");
                      activitySignUpBinding.userName.setErrorEnabled(false);
                  }else{
                      activitySignUpBinding.userName.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activitySignUpBinding.emailChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    activitySignUpBinding.userEmail.setHelperText("Invalid email address");
                    activitySignUpBinding.userEmail.setErrorEnabled(false);
                } else {
                    activitySignUpBinding.userEmail.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activitySignUpBinding.phoneChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 String phone = s.toString();
                 if (phone.length() < 10){
                     activitySignUpBinding.UserPhone.setHelperText("Phone number must contains 10 digits");
                     activitySignUpBinding.UserPhone.setErrorEnabled(false);
                 }
                 else {
                     activitySignUpBinding.UserPhone.setHelperText("");
                 }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activitySignUpBinding.passChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if(!PASSWORD_PATTERN.matcher(password).matches()){
                    activitySignUpBinding.userPassword.setHelperText("password contains min 8 character ,1 special character , no spaces....");
                    activitySignUpBinding.userPassword.setErrorEnabled(false);
                }else {
                    activitySignUpBinding.userPassword.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ////////registration///////////
        activitySignUpBinding.SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }


    ///register Function///
    public void registerUser() {

        ///Hooks///
        String FullName = Objects.requireNonNull(activitySignUpBinding.userFullName.getEditText()).getText().toString();
        String UserName = Objects.requireNonNull(activitySignUpBinding.userName.getEditText()).getText().toString();
        String Email = Objects.requireNonNull(activitySignUpBinding.userEmail.getEditText()).getText().toString();
        String Phone = Objects.requireNonNull(activitySignUpBinding.UserPhone.getEditText()).getText().toString();
        String Password = Objects.requireNonNull(activitySignUpBinding.userPassword.getEditText()).getText().toString();

        ////validate empty data////
        if (FullName.isEmpty()) {
            customDialogBox();
        } else if (UserName.isEmpty()) {
            customDialogBox();
        } else if (Email.isEmpty()) {
            customDialogBox();
        } else if (Phone.isEmpty()) {
            customDialogBox();
        } else if (Password.isEmpty()) {
            customDialogBox();
        } else {

            activitySignUpBinding.progressBarRegister.setVisibility(View.VISIBLE);

            ////registration with firebase authentication/////
            FirebaseAuth userAuth = FirebaseAuth.getInstance();
            userAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = userAuth.getCurrentUser();
                        ///Save user into realtime database /////
                        UserClass userModel = new UserClass(firebaseUser.getUid(), FullName, UserName, Email, Phone, Password);
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
                        dbRef.child(Objects.requireNonNull(firebaseUser).getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //////or debugging purpose///////
                                if (task.isSuccessful()) {
                                    activitySignUpBinding.progressBarRegister.setVisibility(View.GONE);
                                    /////redirecting user to main dashboard///
                                    Intent intent = new Intent(SignUp.this, MainDashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    try {
                                        throw task.getException();
                                    }catch (FirebaseAuthInvalidUserException e){
                                        EmailDialogBox();
                                        activitySignUpBinding.progressBarRegister.setVisibility(View.GONE);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        activitySignUpBinding.progressBarRegister.setVisibility(View.GONE);
                                    }

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


    @SuppressLint("MissingInflatedId")
    public void customDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        View view = LayoutInflater.from(SignUp.this).inflate(R.layout.vaildate_empty_field_dialog, null);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        View view = LayoutInflater.from(SignUp.this).inflate(R.layout.email_alreday, null);
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