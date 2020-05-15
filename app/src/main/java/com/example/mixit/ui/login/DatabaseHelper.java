package com.example.mixit.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context){
        super(context, "login.db", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE userData(username TEXT PRIMARY KEY,name TEXT, password TEXT )");
        db.execSQL("CREATE TABLE favorites(username TEXT PRIMARY KEY, favorite TEXT, FOREIGN KEY (username) REFERENCES userData(username))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userData");
        onCreate(db);
    }

    public boolean addUser(String username, String name, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username.toLowerCase());
        contentValues.put("name", name);
        contentValues.put("password", pass);
        ContentValues cv = new ContentValues();
        cv.put("username", username.toLowerCase());
        cv.put("favorite", "");
        long ins = db.insert("userData", null, contentValues);
        db.insert("favorites", null, cv);
        if (ins==-1) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }
    //check username during signup
    public boolean checkUser(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from userData WHERE username=?", new String[] {username});
        if(cursor.getCount()>0) {
            cursor.close();
            db.close();
            return false;
        }
        else
        {
            cursor.close();
            db.close();
            return true;
        }
    }
    //check username and passoword
    public boolean checkLog(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userData WHERE username=? and password=?", new String[] {username, password});
        if(cursor.getCount()>0) {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }

    }

    //favorites
    public void addFav(String username, int favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        String favTEMP = ""+favorite;
        String str= "SELECT favorite FROM favorites where username=?";
        Cursor cursor = db.rawQuery(str, new String[]{username});
        if(cursor != null)
        {
            while (cursor.moveToNext()){
                favTEMP = favTEMP+","+cursor.getString(0);
            }
        }
        cursor.close();

        db = getWritableDatabase();
        db.execSQL("UPDATE favorites SET favorite = '" + favTEMP + "' WHERE username = '" + username + "' ;");
        db.close();
    }
    public boolean removeFav(String username, int fav){
        SQLiteDatabase db = this.getWritableDatabase();
        String str = "";
        ArrayList<Integer> favs = getIdFavorites(username);
        ArrayList<Integer> removeList = favs;

        for(int i = 0; i < favs.size(); i++){
            if(favs.get(i) == fav){
                removeList.remove(i);
            }
        }
        favs = removeList;
        for(Integer id : favs)
        {
            str += id+ ",";
        }
        if(!str.isEmpty()) {
            if (str.charAt(str.length()-1) == ',') {
                str = str.substring(0, str.length() - 1);
            }
        }
        db.execSQL("UPDATE favorites SET favorite = '" + str + "' WHERE username = '" + username + "' ;");
        return true;
    }



    //retrieve favorites
    public ArrayList<Integer> getIdFavorites(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> list_id = new ArrayList<>();
        String str = "";
        Cursor cursor = db.rawQuery("SELECT favorite from favorites where username =?", new String[]{username});
        if(cursor!=null)
        {
            while(cursor.moveToNext()){
                str = cursor.getString(0);
            }
        }
        List<String> temp = Arrays.asList(str.split(","));
        for(int i=0;i<temp.size();i++)
        {
            if(!temp.get(i).isEmpty()) {
                list_id.add(Integer.parseInt(temp.get(i)));
            }
        }
        return list_id;
    }

    public void updatePassword(String newPass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE userData SET password=?", new String[]{newPass});
        db.close();
    }
}
