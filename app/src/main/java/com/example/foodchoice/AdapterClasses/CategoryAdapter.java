package com.example.foodchoice.AdapterClasses;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryModel> modelArrayList;
    Context context;
    int row_index = -1;

    public CategoryAdapter(ArrayList<CategoryModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ////Create View////
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_name_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       //set category Names////
        holder.catName.setText(modelArrayList.get(position).getCategoryName());

        ///checking item selected///
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
            }
        });
        if (row_index == position){
            holder.linearLayout.setBackgroundResource(R.drawable.home_category_name_selected);
        }else {
            holder.linearLayout.setBackgroundResource(R.drawable.home_category_name_bg);
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ////view Variable////
        TextView catName;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.CategoryName);
            linearLayout = itemView.findViewById(R.id.catNameLayout);

        }
    }
}
