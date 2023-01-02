package com.example.foodchoice.Main.MealPlan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodchoice.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DinnerFragment extends Fragment {
    FloatingActionButton dinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dinner, container, false);


       dinner = view.findViewById(R.id.AddDnRecipe);




        return  view;
    }
}