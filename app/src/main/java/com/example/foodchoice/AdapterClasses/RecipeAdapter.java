package com.example.foodchoice.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    Context context;
    ArrayList<RecipeModel> recipeModelArrayList;

    public RecipeAdapter(Context context, ArrayList<RecipeModel> recipeModelArrayList) {
        this.context = context;
        this.recipeModelArrayList = recipeModelArrayList;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recipe_list,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
       holder.RecipeNameCard.setText(recipeModelArrayList.get(position).getRecipeName());
       holder.RecipeServing.setText(recipeModelArrayList.get(position).getRecipeServing());
       holder.RecipeTimingCard.setText(recipeModelArrayList.get(position).getRecipeName());
       holder.recipeDescriptionCard.setText(recipeModelArrayList.get(position).getRecipeDescription());
        Glide.with(holder.RecipeImage.getContext()).load(recipeModelArrayList.get(position).getRecipeImageUrl()).into(holder.RecipeImage);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView RecipeNameCard,RecipeServing,RecipeTimingCard,recipeDescriptionCard;
        ImageView RecipeImage;
        CardView RecipeCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RecipeNameCard = itemView.findViewById(R.id.RecipeNameCard);
            RecipeServing = itemView.findViewById(R.id.RecipeServing);
            RecipeTimingCard = itemView.findViewById(R.id.RecipeTiming);
            recipeDescriptionCard = itemView.findViewById(R.id.recipeDescriptionCard);
            RecipeImage = itemView.findViewById(R.id.RecipeImage);

        }
    }
}
