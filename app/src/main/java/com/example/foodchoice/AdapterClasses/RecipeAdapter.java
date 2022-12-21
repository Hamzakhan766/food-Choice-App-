package com.example.foodchoice.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoice.HelperClasses.RecipeModel;
import com.example.foodchoice.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    ArrayList<RecipeModel> recipeModelArrayList;
    Context context;

    public RecipeAdapter(ArrayList<RecipeModel> recipeModelArrayList, Context context) {
        this.recipeModelArrayList = recipeModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recipe_list,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {

     holder.imageView.setImageResource(recipeModelArrayList.get(position).getThumbnailImage());
     holder.textView.setText(recipeModelArrayList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return recipeModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.thumbnailImage);
            textView = itemView.findViewById(R.id.categoryTitle);

        }
    }
}
