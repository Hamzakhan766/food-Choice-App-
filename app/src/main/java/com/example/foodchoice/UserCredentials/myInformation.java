package com.example.foodchoice.UserCredentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.foodchoice.HelperClasses.UserClass;
import com.example.foodchoice.databinding.ActivityMyInformationBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myInformation extends AppCompatActivity {
    ActivityMyInformationBinding myInformationBinding;
    FirebaseAuth userAuth;
    Uri uri;
    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myInformationBinding = ActivityMyInformationBinding.inflate(getLayoutInflater());
        setContentView(myInformationBinding.getRoot());

        myInformationBinding.myInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ProfileInformation();

    }

    private void ProfileInformation(){
         userAuth = FirebaseAuth.getInstance();
        FirebaseUser userId = userAuth.getCurrentUser();
        DatabaseReference userPro = FirebaseDatabase.getInstance().getReference("Users");

        userPro.child(userId.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserClass usermodel = snapshot.getValue(UserClass.class);

                    String fullname = usermodel.getUser_fullName();
                    String username = usermodel.getUser_UserName();
                    String email = usermodel.getUser_UseEmail();
                    String phone = usermodel.getUser_UserPhone();

                    myInformationBinding.infoName.setText(fullname);
                    myInformationBinding.infoUserName.setText(username);
                    myInformationBinding.myInfoEmail.setText(email);
                    myInformationBinding.myInfoPhone.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



//    private void showProfile() {
//
//        userAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = userAuth.getCurrentUser();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//
//        ////retrieve userData ////
//        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                UserClass  readUser = snapshot.getValue(UserClass.class);
//                if(readUser != null){
//                    String fullName = readUser.getUser_fullName();
//                    String userName = readUser.getUser_UserName();
//                    String userEmail = readUser.getUser_UseEmail();
//                    String userPhone = readUser.getUser_UserPhone();
//                    String userPassword = readUser.getUser_UserPassword();
//
//                userProfileBinding.updateFullName.getEditText().setText(fullName);
//                userProfileBinding.updateUserName.getEditText().setText(userName);
//                userProfileBinding.updateEmail.getEditText().setText(userEmail);
//                userProfileBinding.updatePhone.getEditText().setText(userPhone);
//                userProfileBinding.updatePassword.getEditText().setText(userPassword);
//
//                }
//               else {
//                    Toast.makeText(myInformation.this, "Empty Result", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }


//    public void uploadImage(){
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("UserImage").child(uri.getLastPathSegment());
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete());
//                Uri userImageUrl = uriTask.getResult();
//                imageUrl = userImageUrl.toString();
//
//
//            }
//        });
//    }


}