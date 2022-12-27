package com.example.foodchoice.UserCredentials;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.foodchoice.databinding.ActivityMyInformationBinding;

import com.google.firebase.auth.FirebaseAuth;

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