package com.example.mixit.ui.discover;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.example.mixit.ui.recipes.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class DBHelper extends SQLiteOpenHelper
{
    private int recipeNumber;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "recipeList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL("CREATE TABLE IF NOT EXISTS recipeList(recipeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeName TEXT, ingredients TEXT, method TEXT, ingCount INTEGER, resid INTEGER)");
        db.execSQL("PRAGMA case_sensitive_like = true;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipeList");
        onCreate(db);
    }

    //returns the recipe number that is going to be assigned to the recipeID
    public int getRecipeNumber(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT max(recipeID) from recipeList", null);
        if(cursor!=null)
        {
            while(cursor.moveToNext())
            {
                recipeNumber = cursor.getInt(0)+1;
            }
        }
        return recipeNumber;
    }

    //insert function can also be used as publish function
    public void recipeInsert(String recipeName,  String ingredients, String method, int ingCount, int resid) {


        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO recipeList VALUES(?,?,?,?,?,?)";
        SQLiteStatement p = db.compileStatement(sql);
        p.bindNull(1);
        p.bindString(2, recipeName);
        p.bindString(3, ingredients);
        p.bindString(4, method);
        p.bindLong(5, ingCount);
        p.bindLong(6, resid);
        p.executeInsert();
    }

    //SEARCH BY INGREDIENTS
    // change the where statement some how need the items to be scanned in the whole thing instead of compared
    // [eggs, milk]
    public HashMap<Integer, Integer> ingredients_selectRecipeByIngredientName(ArrayList<String> ingredientsName){
        SQLiteDatabase db = getReadableDatabase();
        HashMap<Integer, Integer> idRecipes = new HashMap<Integer, Integer>();

        String strNames = "";
        for (int i=0; i < ingredientsName.size(); i++)
        {
            strNames += "ingredients LIKE '%" + ingredientsName.get(i) + "%'";
            if (i != ingredientsName.size()-1)
            {
                strNames += " AND ";
            }
        }

        strNames = "SELECT recipeID, ingCount FROM recipeList WHERE "+ strNames;
        Cursor cursor = db.rawQuery(strNames, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                idRecipes.put(cursor.getInt(0), cursor.getInt(1));
            }
        }
        cursor.close();
        db.close();
        return idRecipes;
    }

    //RETURNS RECIPE SEARCHED BY NAME, USED FOR THE DISOCVER SEARCH
    //add exception for no item returned
    public ArrayList<Recipe> recipes_SelectByName(String name)
    {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM recipeList WHERE recipeName LIKE '%" + name + "%' ", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                );
                recipeList.add(recipe);
                //returns the recipe searched by the name of the recipe
            }
        }
        return  recipeList;
    }

    //FAVORITES TAB
    public Recipe recipes_SelectById(int id)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM recipeList WHERE recipeID = " + id, null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                );
                cursor.close();
                db.close();
                return recipe;
                //returns the recipe by the id use for favorties tab
            }
        }
        cursor.close();
        db.close();
        return  null;
    }

    public int getCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM recipeList", null);
        int count=0;
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                count = cursor.getInt(0);
            }
        }
        cursor.close();
        db.close();

        return count;
    }

    public void clearDatabase(){
        SQLiteDatabase db = getReadableDatabase();
        String cleardb = "DELETE FROM recipeList";
        db.execSQL(cleardb);
    }

    public ArrayList<Recipe> randomRecipe(){
        SQLiteDatabase db = this.getReadableDatabase();
        HashSet<Recipe> list = new HashSet<Recipe>();
        while(list.size()<6){
            int rand = (int) (Math.random()*getCount()+1);
            Recipe r = this.recipes_SelectById(rand);
            list.add(r);
        }
        db.close();
        return new ArrayList<Recipe>(list);
    }

    public ArrayList<Recipe> recipes_SelectAll()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Recipe> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM recipeList", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }
}

