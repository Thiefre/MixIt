package com.example.mixit.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mixit.R;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText newPassword,confirmPassword;
    Button changePasswordBtn;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        db = new DatabaseHelper(this);

        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmNewPassword);
        changePasswordBtn = findViewById(R.id.changePassword);

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassword.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                if(newPass.equals(confirmPass)){
                    db.updatePassword(newPass);
                    Toast.makeText(getApplicationContext(), "Password has been updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
