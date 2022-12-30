package com.example.foodchoice.Main.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodchoice.AdapterClasses.RecipeAdapter;
import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.R;

import java.util.ArrayList;

public class frRecipeAdapter extends RecyclerView.Adapter<frRecipeAdapter.ViewHolder> {

    Context context;
    ArrayList<RecipeModel> recipeModelArrayList;

    public frRecipeAdapter(Context context, ArrayList<RecipeModel> recipeModelArrayList) {
        this.context = context;
        this.recipeModelArrayList = recipeModelArrayList;
    }

    @NonNull
    @Override
    public frRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabs_recipe,parent,false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull frRecipeAdapter.ViewHolder holder, int position) {
        holder.RecipeTitle.setText(recipeModelArrayList.get(position).getRecipeName());
        Glide.with(holder.recipeImage.getContext()).load(recipeModelArrayList.get(position).getRecipeImageUrl()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView RecipeTitle;
        ImageView recipeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            RecipeTitle = itemView.findViewById(R.id.RecipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);

        }
    }
}
