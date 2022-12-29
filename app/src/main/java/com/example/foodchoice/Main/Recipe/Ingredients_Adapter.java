package com.example.foodchoice.Main.Recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodchoice.HelperClasses.GroceryModel;
import com.example.foodchoice.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Ingredients_Adapter extends RecyclerView.Adapter<Ingredients_Adapter.ViewHolder> {
    ArrayList<GroceryModel> groceryModelArrayList;
    Context context;
    ArrayList<String> ingredients= new ArrayList<>();
    ingredientsQuantityListner quantityItems;

    public Ingredients_Adapter(ArrayList<GroceryModel> groceryModelArrayList, Context context, ingredientsQuantityListner quantityItems) {
        this.groceryModelArrayList = groceryModelArrayList;
        this.context = context;
        this.quantityItems = quantityItems;
    }

    @NonNull
    @Override
    public Ingredients_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_selected,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ingredients_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(groceryModelArrayList.get(position).getGroceryImageUrl()).into(holder.ingredient_img);
        holder.ingredient_name.setText(groceryModelArrayList.get(position).getGroceryName());
        holder.checkBox_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox_ing.isChecked()){
                    ingredients.add(groceryModelArrayList.get(position).getGroceryName());
                }else {
                    ingredients.remove(groceryModelArrayList.get(position).getGroceryName());
                }
                quantityItems.ingredientsQuantityChange(ingredients);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox_ing;
        TextView ingredient_name;
        ImageView ingredient_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox_ing = itemView.findViewById(R.id.checkBox_ing);
            ingredient_name = itemView.findViewById(R.id.ingredient_name);
            ingredient_img = itemView.findViewById(R.id.ingredient_img);
        }
    }
}
