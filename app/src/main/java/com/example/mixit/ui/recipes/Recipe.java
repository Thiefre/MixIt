package com.example.mixit.ui.recipes;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    private String title;
    private ArrayList<String> ingredients = new ArrayList<String>();
    private int resid;
    private String instructions;

    public Recipe(String title, ArrayList<String>ingredients, int resid, String instructions)
    {
        this.title = title;
        this.ingredients = ingredients;
        this.resid = resid;
        this.instructions = instructions;
    }

    public void addIngredient(String ingredient)
    {
        ingredients.add(ingredient);
    }

    public void removeIngredient(String ingredient)
    {
        ingredients.remove(ingredient);
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public int getResid()
    {
        return resid;
    }

    public String getInstructions()
    {
        return instructions;
    }
    public String ingredientsToString()
    {
        return ingredients.toString();
    }

    public ArrayList<String> getIngredientList()
    {
        return ingredients;
    }
}
