package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Account extends AppCompatActivity {
    EditText e1;
    Button b1;
    DatabaseHelper db;
    ArrayList<String> a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.username);
        b1 = (Button) findViewById(R.id.addbtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                if (s1.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Integer> l = db.getIdFavorites(s1);
                    for(int i: l){
                        System.out.println(i);
                    }
                }
            }
        });
    }
}
