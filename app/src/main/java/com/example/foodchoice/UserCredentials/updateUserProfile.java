package com.example.foodchoice.UserCredentials;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.databinding.ActivityUpdateUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class updateUserProfile extends AppCompatActivity {
    ActivityUpdateUserProfileBinding userProfileBinding;
    FirebaseAuth userAuth;
    Uri uri;
    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userProfileBinding = ActivityUpdateUserProfileBinding.inflate(getLayoutInflater());
        setContentView(userProfileBinding.getRoot());


        userProfileBinding.updateUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectImage();
            }
        });
        userProfileBinding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        showProfile();

    }

    private void showProfile() {

        userAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = userAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        ////retrieve userData ////
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

    public void selectImage(){
       Intent imagePicker  = new Intent(Intent.ACTION_PICK);
       imagePicker.setType("image/*");
       startActivityForResult(imagePicker,1);
    }

    public void uploadImage(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("UserImage").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri userImageUrl = uriTask.getResult();
                imageUrl = userImageUrl.toString();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            uri = data.getData();
            userProfileBinding.updateUserImage.setImageURI(uri);
        }else {
            Toast.makeText(this, "Please pick image", Toast.LENGTH_SHORT).show();
        }


    }
}