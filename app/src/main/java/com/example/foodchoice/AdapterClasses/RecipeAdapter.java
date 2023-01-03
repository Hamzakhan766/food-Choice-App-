package com.example.foodchoice.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.Main.Recipe.RecipeIndex;
import com.example.foodchoice.Main.Recipe.SingleRecipeDetails;
import com.example.foodchoice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoryModel> catList ;
    ArrayList<RecipeModel> recipeModelArrayList;
    public RecipeAdapter(Context context, ArrayList<RecipeModel> recipeModelArrayList) {
        this.context = context;
        this.recipeModelArrayList = recipeModelArrayList;
catList=new ArrayList<CategoryModel>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Categories");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for (DataSnapshot ds : snapshot1.getChildren()) {
                    catList.add(ds.getValue(CategoryModel.class));
                    Log.d("Read Cat","Hello");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recipe_list,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       holder.RecipeNameCard.setText(recipeModelArrayList.get(position).getRecipeName());
       holder.RecipeServing.setText("Serving: "+recipeModelArrayList.get(position).getRecipeServing());
       holder.recipeDescriptionCard.setText(recipeModelArrayList.get(position).getRecipeDescription());
       String cid=recipeModelArrayList.get(position).getRecipeCategoryID();
        for (CategoryModel catModel : catList) {
            Log.d("Matches mm",catModel.getCategoryId());
            if(catModel.getCategoryId().equals(cid))
            {
                holder.RecCategoryName.setText(catModel.getCategoryName());
            }
        }



        Glide.with(holder.RecipeImage.getContext()).load(recipeModelArrayList.get(position).getRecipeImageUrl()).into(holder.RecipeImage);

        holder.RecipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleRecipeDetails.class);
                intent.putExtra("SingleRecipeId",recipeModelArrayList.get(position).getRecipeID());
                intent.putExtra("SingleRecipeImage",recipeModelArrayList.get(position).getRecipeImageUrl());
                intent.putExtra("SingleRecipeName",recipeModelArrayList.get(position).getRecipeName());
                intent.putExtra("SingleRecipeDescription",recipeModelArrayList.get(position).getRecipeDescription());
                intent.putExtra("SingleRecipeDirection",recipeModelArrayList.get(position).getRecipeDirection());
                intent.putExtra("SingleRecipeServing",recipeModelArrayList.get(position).getRecipeServing());
                intent.putExtra("SingleRecipeOIngredients",recipeModelArrayList.get(position).getRecipeIngredients());
                intent.putExtra("SingleRecipeVideo",recipeModelArrayList.get(position).getRecipeVideoUrl());
                intent.putExtra("SingleRecipeCategoryName",recipeModelArrayList.get(position).getRecipeCategoryID());
                intent.putExtra("SingleRecipeUser",recipeModelArrayList.get(position).getUserID());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(new Intent(intent));
            }
        });


    }

    @Override
    public int getItemCount() {
        return recipeModelArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void FilterList(ArrayList<RecipeModel> filterRecipe) {
        this.recipeModelArrayList = filterRecipe;
        notifyDataSetChanged();
    }

    public void RecipeFilter(ArrayList<RecipeModel> recipeFilterList) {
        this.recipeModelArrayList = recipeFilterList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView RecipeNameCard,RecipeServing,recipeDescriptionCard,RecCategoryName;
        ImageView RecipeImage;
        CardView RecipeCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RecipeNameCard = itemView.findViewById(R.id.RecipeNameCard);
            RecipeServing = itemView.findViewById(R.id.RecipeServing);
            recipeDescriptionCard = itemView.findViewById(R.id.recipeDescriptionCard);
            RecCategoryName = itemView.findViewById(R.id.RecCategoryName);
            RecipeImage = itemView.findViewById(R.id.RecipeImage);

            RecipeCardView = itemView.findViewById(R.id.RecipeCardView);

        }
    }
}
