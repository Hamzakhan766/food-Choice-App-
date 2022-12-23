package com.example.foodchoice.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchoice.HelperClasses.GroceryModel;
import com.example.foodchoice.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    Context context;
    ArrayList<GroceryModel> groceryModelArrayList;

    public GroceryAdapter(Context context, ArrayList<GroceryModel> groceryModelArrayList) {
        this.context = context;
        this.groceryModelArrayList = groceryModelArrayList;
    }

    @NonNull
    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryAdapter.ViewHolder holder, int position) {
        holder.groceryName.setText(groceryModelArrayList.get(position).getGroceryName());
    }

    @Override
    public int getItemCount() {
        return groceryModelArrayList.size();
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder{
        TextView groceryName;
        CircleImageView groceryImageUrl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            groceryName = itemView.findViewById(R.id.groceryName);
            groceryImageUrl = itemView.findViewById(R.id.groceryImage);

        }
    }
}
