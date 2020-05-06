package com.example.recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper
{
    private int recipeNumber;

    public DBHelper(Context context) {
        super(context, "recipe.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL("CREATE TABLE recipeList(recipeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeName TEXT, ingredients TEXT, method TEXT, thumbnail BLOB, mainIMG BLOB, ingCount TEXT)");

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
    public void recipeInsert(int recipeID, String recipeName,  String ingredients, String method,
                               byte[] thumbnail, byte[] mainImg, int ingCount) {

        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement p = db.compileStatement("INSERT INTO recipeList values(?,?,?,?,?,?,?,?,?,?);");
        p.bindNull(1);
        p.bindLong(2, recipeID);
        p.bindString(3, recipeName);
        p.bindString(4, ingredients);
        p.bindString(5, method);
        p.bindBlob(8, thumbnail);
        p.bindBlob(9, mainImg);
        p.bindLong(10, ingCount);
        p.execute();
        recipeNumber = recipeID+1;
        db.close();
        //method 2?
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("recipeID", recipeID);
//        contentValues.put("recipeName", recipeName);
//        contentValues.put("ingredients", ingredients);
//        contentValues.put("method", method);
//        contentValues.put("thumbnail", thumbnail);
//        contentValues.put("mainIMG", mainImg);
//        contentValues.put("ingCount", ingCount);
//        db.insert("recipeList", null,contentValues);
//        ??db.close();
    }

    //this method lets the user publish recipe into the database
    public void publishRecipe(String recipeName,  String ingredients, String method,
    byte[] thumbnail, byte[] mainImg, int ingCount)
    {
        SQLiteDatabase db = getWritableDatabase();
        getRecipeNumber();
        SQLiteStatement p = db.compileStatement("INSERT INTO recipeList values(?,?,?,?,?,?,?,?,?,?);");
        p.bindNull(1);
        p.bindLong(2, recipeNumber);
        p.bindString(3, recipeName);
        p.bindString(4, ingredients);
        p.bindString(5, method);
        p.bindBlob(8, thumbnail);
        p.bindBlob(9, mainImg);
        p.bindLong(10, ingCount);
        p.execute();
        recipeNumber = recipeNumber+1;
        p.execute();
    }

    //SEARCH BY INGREDIENTS
    // change the where statement some how need the items to be scanned in the whole thing instead of compared
    public HashMap<Integer, Integer> ingredients_selectRecipeByIngredientName(ArrayList<String> ingredientsName){
        //open database
        SQLiteDatabase db = getReadableDatabase();
        //get all ids of recipes and then use get recipe by id function
        //use a key value pair to get recipe id and ingredient count
        HashMap<Integer, Integer> idRecipes = new HashMap<Integer, Integer>();
        //use this for the where part of sql query
        String strNames = "";
        // combine everything into one string EVERYTHING OK
        for (int i=0; i < ingredientsName.size(); i++)
        {
            strNames += "ingredients = '" + ingredientsName.get(i) + "'";
            if (i != ingredientsName.size()-1)
            {
                strNames += " OR ";
            }
        }
        // this is right algo but idk about syntax
        Cursor cursor = db.rawQuery("SELECT recipeID, count(*) FROM recipeList WHERE ? GROUP BY recipeID", new String[]{strNames});
        //what the actual fuck does this do
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //adds recipe id to the hashmap
                idRecipes.put(cursor.getInt(0), cursor.getInt(1));
            }
        }
        cursor.close();
        db.close();
        // all recipes with satisfy half the condition still need to check for the numbers
        return idRecipes;
    }

    //DISCOVER RANDOM FUNCTION
    public ArrayList<RecipeItem> getRecipeRandom(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RecipeItem> rList = new ArrayList<RecipeItem>();
        int[] rand = new int[10];
        while(rList.size()<10){
            int randomID = (int) Math.random()*20+1;
            for(int i=0;i<rand.length;i++)
            {
                if(randomID != rand[i])
                {
                    //call get recipe by id function
                    RecipeItem r = recipes_SelectById(randomID);
                    rList.add(r);
                }
            }
        }
        return rList;
    }

    //RETURNS RECIPE SEARCHED BY NAME, USED FOR THE DISOCVER SEARCH
    public RecipeItem recipes_SelectByName(String name)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM recipeList WHERE recipeName=?", new String[]{name});
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeItem recipe = new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getBlob(5),
                        cursor.getInt(6)
                );
                cursor.close();
                db.close();
                return recipe;
                //returns the recipe searched by the name of the recipe
            }
        }
        return  null;
    }

    //FAVORITES TAB
    public RecipeItem recipes_SelectById(int id)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM recipeList WHERE _id = " + id, null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeItem recipe = new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getBlob(5),
                        cursor.getInt(6)
                );
                cursor.close();
                db.close();
                return recipe;
                //returns the recipe by the id use for favorties tab
            }
        }
        return  null;
    }
}