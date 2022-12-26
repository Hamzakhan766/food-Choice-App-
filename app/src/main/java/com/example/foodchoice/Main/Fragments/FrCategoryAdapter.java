package com.example.foodchoice.Main.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoice.HelperClasses.CategoryModel;
import com.example.foodchoice.R;

import java.util.ArrayList;

public class FrCategoryAdapter extends RecyclerView.Adapter<FrCategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryModel> categoryModelArrayList;
    int row_index = -1;

    public FrCategoryAdapter(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
    }

    @NonNull
    @Override
    public FrCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_name_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrCategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryName.setText(categoryModelArrayList.get(position).getCategoryName());
        holder.catLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
            }
        });
        if (row_index == position)
            holder.catLayout.setBackgroundResource(R.drawable.home_category_name_selected);
        else
            holder.catLayout.setBackgroundResource(R.drawable.home_category_name_bg);
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        LinearLayout catLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.CategoryName);
            catLayout = itemView.findViewById(R.id.catNameLayout);

        }
    }
}
