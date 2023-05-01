package com.example.phpintegrationonandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phpintegrationonandroid.Models.User;

public class AccountActivity extends AppCompatActivity {
    Button btnEdit, btnChangePassword, btnLogout;
    EditText editTextFirstname, editTextMiddlename, editTextLastname,editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toast.makeText(this, "welcome back!", Toast.LENGTH_SHORT).show();

        editTextFirstname = findViewById(R.id.edit_text_firstname);
        editTextLastname = findViewById(R.id.edit_text_lastname);
        editTextMiddlename = findViewById(R.id.edit_text_middlename);
        editTextEmail = findViewById(R.id.edit_text_email);

        btnEdit = findViewById(R.id.btn_save);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnLogout = findViewById(R.id.btn_log_out);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, EditAccount.class));
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, ChangePassword.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, Login.class));
                finish();
                Toast.makeText(AccountActivity.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
            }
        });

        loadUserInfo();
    }

    private void loadUserInfo(){
        User user = AppManager.getUser(this);
        if(user == null){
            //if not logged in
            startActivity(new Intent(AccountActivity.this, Login.class));
        }else{
            editTextFirstname.setText(user.getFirstname());
            editTextMiddlename.setText(user.getMiddlename());
            editTextLastname.setText(user.getLastname());
            editTextEmail.setText(user.getEmail());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }
}