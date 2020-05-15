package com.example.mixit.ui.recipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mixit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class RecipeButton extends androidx.appcompat.widget.AppCompatButton implements Target {

    public RecipeButton(Context context) {
        super(context);
    }

    public RecipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecipeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setBackground(new BitmapDrawable(getResources(), bitmap));
        Log.i("SUCCESS", "BUTTON SET");
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        Log.i("FAIL", "BUTTON FAILED");
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        //Set your placeholder
        Log.i("PREPARE", "BUTTON PREPARE");
    }

    public void setRecipeListener(Recipe recipe, FragmentManager fragmentManager)
    {
        this.setOnClickListener(new RecipeListener(recipe, fragmentManager));
    }

    class RecipeListener implements OnClickListener
    {
        Recipe recipe;
        FragmentManager fragmentManager;
        public RecipeListener(Recipe recipe, FragmentManager fragmentManager)
        {
            this.recipe = recipe;
            this.fragmentManager = fragmentManager;
        }
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.nav_host_fragment, new RecipeFragment(recipe)).addToBackStack("Discover").commit();
            System.out.println("Current recipe "+ recipe.getTitle());
        }
    }
}
