package com.example.foodchoice.Main.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.databinding.ActivitySingleRecipeDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SingleRecipeDetails extends AppCompatActivity {
    ActivitySingleRecipeDetailsBinding singleRecipeDetailsBinding;
    ArrayList<CategoryModel> catList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        singleRecipeDetailsBinding =ActivitySingleRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(singleRecipeDetailsBinding.getRoot());

        singleRecipeDetailsBinding.SingleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        //////get details form single recipe items//////
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("SingleRecipeImage")).into(singleRecipeDetailsBinding.SingleRecipeImage);
        singleRecipeDetailsBinding.SingleRecipeName.setText(getIntent().getStringExtra("SingleRecipeName"));
        singleRecipeDetailsBinding.SingleRecipeDescription.setText(getIntent().getStringExtra("SingleRecipeDescription"));
        singleRecipeDetailsBinding.SingleRecipeServing.setText("Serving: " +getIntent().getStringExtra("SingleRecipeServing"));
        singleRecipeDetailsBinding.SingleRecipeDirection.setText(getIntent().getStringExtra("SingleRecipeDirection"));
        singleRecipeDetailsBinding.SingleRecipeCategory.setText("Category: "+getIntent().getStringExtra("SingleRecipeCategoryName"));
        singleRecipeDetailsBinding.SingleRecipeUserName.setText(getIntent().getStringExtra("SingleRecipeUser"));
        String VideoUri = getIntent().getStringExtra("SingleRecipeVideo");

        singleRecipeDetailsBinding.SingleRecipeVideo.setVideoURI(Uri.parse(VideoUri));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(singleRecipeDetailsBinding.SingleRecipeVideo);
        mediaController.setMediaPlayer(singleRecipeDetailsBinding.SingleRecipeVideo);
        singleRecipeDetailsBinding.SingleRecipeVideo.setMediaController(mediaController);
        singleRecipeDetailsBinding.SingleRecipeVideo.start();

    }

}