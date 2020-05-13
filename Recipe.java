package com.example.mixit.ui.recipes;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    private String title;
    private String ingredients;
    private int id;
    private String instructions;
//    private byte[] thumbnail;
//    private byte[] mainImg;
    private int count;
    private int resid;

    public Recipe(int id, String title, String ingredients, String instructions, int ingCount, int resid)
    {
        this.title = title;
        this.ingredients = ingredients;
        this.id = id;
        this.instructions = instructions;
//        this.thumbnail = thumbnail;
//        this.mainImg = mainImg;
        this.count = ingCount;
        this.resid=resid;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

//    public byte[] getThumbnail() {
//        return thumbnail;
//    }
//
//    public byte[] getMainImg() {
//        return mainImg;
//    }
    public String getTitle()
    {
        return title;
    }

    public int getIngredientCount()
    {
        return count;
    }
    public int getId()
    {
        return id;
    }

    public String getInstructions()
    {
        return instructions;
    }
    public String ingredientsToString()
    {
        return ingredients;
    }

    @Override
    public boolean equals(Object o)
    {
        System.out.println("I was here");
        if(o instanceof Recipe)
        {
            Recipe r = (Recipe) o;
            if(r.getId() == this.id)
            {
                return true;
            }
        }
        return false;
    }
    public int hashCode()
    {
        return id*title.length();
    }

    public int getResid(){
        return resid;
    }

    public void setResid(int resid){
        this.resid = resid;
    }
}
