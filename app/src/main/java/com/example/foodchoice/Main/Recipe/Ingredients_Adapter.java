package com.example.foodchoice.Main.Recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoice.R;

public class Ingredients_Adapter extends RecyclerView.Adapter<Ingredients_Adapter.ViewHolder> {





    @NonNull
    @Override
    public Ingredients_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_selected,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ingredients_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox_ing;
        TextView ingredient_name;
        ImageView ingredient_img;
        EditText ingredients_Specs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox_ing = itemView.findViewById(R.id.checkBox_ing);
            ingredient_name = itemView.findViewById(R.id.ingredient_name);
            ingredient_img = itemView.findViewById(R.id.ingredient_img);
            ingredients_Specs = itemView.findViewById(R.id.ingredients_Specs);


        }
    }
}
