package com.example.foodchoice.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodchoice.R;

public class sliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public sliderAdapter(Context context) {
        this.context = context;
    }

    int[] images = {
            R.drawable.on_boarding_recipe,
            R.drawable.on_boarding_enjoy,
            R.drawable.on_boarding_cook
    };

    int[] titles = {
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title
    };

    int[] descriptions = {
            R.string.first_slide_des,
            R.string.second_slide_des,
            R.string.third_slide_des
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slides_layout,container,false);

        //hooks///
        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView title = view.findViewById(R.id.slider_title);
        TextView description = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        title.setText(titles[position]);
        description.setText(descriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }



}
