package com.example.mixit.ui.discover;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.mixit.R;
import com.example.mixit.ui.recipes.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper
{
    private int recipeNumber;
    private static DBHelper dbHelper;

    private DBHelper(Context context) {
        super(context, "recipeList.db", null , 1);
    }

    public static synchronized DBHelper getInstance(Context context)
    {
        if(dbHelper == null)
        {
            dbHelper = new DBHelper(context);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            if(pref.getBoolean("dbCreated", false) == false) {
                dbHelper.recipeInsert("Basic Omelet", "eggs onion", "Break Eggs then stir them with salt and diced onion and throw into skillet",
                        2, R.drawable.basic_omelet);
                dbHelper.recipeInsert("Baked Omelet", "eggs onion ham cheese", "Beat together the eggs and milk. Add seasoning salt, ham, Cheddar cheese, Mozzarella cheese and minced onion. Pour into prepared casserole dish. Bake at 350 degrees",
                        4, R.drawable.baked_omelet);
                dbHelper.recipeInsert("Grilled Cheese Sandwich", "bread butter cheese onion tomato", "Butter the bread, put cheese between two slices, shove some onions in, fry until brown",
                        5, R.drawable.grilled_cheese);
                dbHelper.recipeInsert("Chicken Salad", "chicken mayonnaise celery lemon pepper tomato", "Mix mayonnaise, lemon, pepper in a bowl, throw chicken, tomato and celery on there. Done",
                        6, R.drawable.chicken_salad);
                dbHelper.recipeInsert("Basic Scrambled Eggs", "eggs milk salt pepper butter", "BEAT eggs, milk, salt and pepper in medium bowl until blended.\nHEAT butter in large nonstick skillet over medium heat until hot. POUR in egg mixture. As eggs begin to set, gently PULL the eggs across the pan with a spatula, forming large soft curds.\nCONTINUE cooking—pulling, lifting and folding eggs—until thickened and no visible liquid egg remains. Do not stir constantly. REMOVE from heat. SERVE immediately.",
                        6, R.drawable.basic_scrambled_eggs);
                dbHelper.recipeInsert("Beef and Broccoli", "cornstarch steak soy_sauce sugar garlic ginger oil broccoli onions", "In a large bowl, whisk together 2 tablespoons cornstarch with 3 tablespoons water. Add the beef to the bowl and toss to combine.\nIn a separate small bowl, whisk together the remaining 1 tablespoon cornstarch with the soy sauce, brown sugar, garlic and ginger. Set the sauce aside.\n" +
                                "Heat a large nonstick sauté pan over medium heat. Add 1 tablespoon of the vegetable oil and once it is hot, add the beef and cook, stirring constantly until the beef is almost cooked through. Using a slotted spoon, transfer the beef to a plate and set it aside.\n" +
                                "Add the remaining 1 tablespoon of vegetable oil to the pan and once it is hot, add the broccoli florets and sliced onions and cook, stirring occasionally, until the broccoli is tender, about 4 minutes.\n" +
                                "Return the beef to the pan then add the prepared sauce. Bring the mixture to a boil and cook, stirring, for 1 minute or until the sauce thickens slightly. Serve with rice or noodles.",
                        2, R.drawable.beef_and_broccoli);
                pref.edit().putBoolean("dbCreated", true).commit();
            }
        }
        return dbHelper;
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
        db.close();
    }

    //SEARCH BY INGREDIENTS
    // change the where statement some how need the items to be scanned in the whole thing instead of compared
    // [eggs, milk]
    public ArrayList<Integer> ingredients_selectRecipeByIngredientName(ArrayList<String> ingredientsName){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> idRecipes = new ArrayList<>();
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
                idRecipes.add(cursor.getInt(0));
            }
        }
        cursor.close();
        db.close();
        return idRecipes;
    }

    public int getIngCount(int recipeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int ingCount=0;
        Cursor cursor = db.rawQuery("SELECT ingCount from recipeList where recipeID= "+recipeID, null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                ingCount = cursor.getInt(0);
            }
        }
        return ingCount;
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
        while(list.size() < getCount() && list.size() < 6){
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