package com.example.discover;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1;
    Button b1,b2;
    ListView l1;
    ArrayList<String> recipeIng;
    ArrayAdapter<String> adapter;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        e1 = (EditText) findViewById(R.id.ingred);
        b1 = (Button) findViewById(R.id.add);
        b2 = (Button) findViewById(R.id.search);
        l1 = (ListView) findViewById(R.id.items_list);
        recipeIng = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipeIng);
        l1.setAdapter(adapter);
        db = new DBHelper(getApplicationContext() , "recipeList.db", null , 1);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add:
                String itemEntered = e1.getText().toString();
                recipeIng.add(itemEntered);
                adapter.notifyDataSetChanged();
                e1.setText("");
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;

            case R.id.search:
                int count = recipeIng.size();
                //search recipe in database
                HashMap<Integer, Integer> h = db.ingredients_selectRecipeByIngredientName(recipeIng);
                //compare hashmap value with count
                for (Map.Entry<Integer, Integer> entry : h.entrySet()) {
                    if(count<entry.getValue()){
                        h.remove(entry.getKey());
                    }
                }
                Toast.makeText(getApplicationContext(), "Recipe Searched" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
