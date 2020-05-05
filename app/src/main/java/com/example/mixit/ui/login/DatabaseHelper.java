package com.example.mixit.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, "login.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE userData(username TEXT PRIMARY KEY,name TEXT, password TEXT )");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userData");

        onCreate(db);
    }

    public boolean addUser(String username, String name, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("name", name);
        contentValues.put("password", pass);

        long ins = db.insert("userData", null, contentValues);
        System.out.println(ins);
        if (ins==-1) return false;
        else return true;
    }
    //check username during signup
    public boolean checkUser(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from userData WHERE username=?", new String[] {username});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //check username and passoword
    public boolean checkLog(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userData WHERE username=? and password=?", new String[] {username, password});
        if(cursor.getCount()>0) return true;
        else return false;

    }
}
