package com.example.mixit.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.accounts.Account;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mixit.MainActivity;
import com.example.mixit.R;
import com.example.mixit.ui.account.AccountFragment;

public class Login extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText) findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean check = db.checkLog(username, password);
                if(check==true){
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                    Boolean loggedIn = pref.edit().putBoolean("loggedIn", true).commit();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Information, Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
