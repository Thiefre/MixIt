package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1, e2, e3, e4;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText) findViewById(R.id.name);
        e3 = (EditText) findViewById(R.id.password);
        e4 = (EditText) findViewById(R.id.cpassword);
        b1 = (Button) findViewById(R.id.register);
        b2 = (Button) findViewById(R.id.login);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                String s4 = e4.getText().toString();
                if (s1.equals("") || s2.equals("") || s3.equals("")||s4.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (s3.equals(s4)) {
                        boolean check = db.checkUser(s1);
                        if (check == true) {
                            Boolean add = db.addUser(s1, s2, s3);
                            if (add == true) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
