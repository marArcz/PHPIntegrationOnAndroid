package com.example.phpintegrationonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phpintegrationonandroid.Models.User;
import com.example.phpintegrationonandroid.Models.UserAuthResponse;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnSave, btnLogin;
    private EditText etFirstName,etMiddleName,etLastName,etEmail,etPassword,etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize objects;

        btnSave = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btn_login);
        etFirstName = findViewById(R.id.edit_text_firstname);
        etMiddleName = findViewById(R.id.edit_text_middlename);
        etLastName = findViewById(R.id.edit_text_lastname);
        etEmail = findViewById(R.id.edit_text_email);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              OnSignUp();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }

    private void OnSignUp(){
        AccountAPI accountAPI = RetrofitClient.getAccountAPI();

        User newUser = new User();
        newUser.setFirstname(etFirstName.getText().toString());
        newUser.setMiddlename(etMiddleName.getText().toString());
        newUser.setLastname(etLastName.getText().toString());
        newUser.setEmail(etEmail.getText().toString());
        newUser.setPassword(etPassword.getText().toString());
        newUser.setConfirm_password(etConfirmPassword.getText().toString());

        Call<UserAuthResponse> call = accountAPI.register(newUser);

        call.enqueue(new Callback<UserAuthResponse>() {
            @Override
            public void onResponse(Call<UserAuthResponse> call, Response<UserAuthResponse> response) {

                if(response.body() == null){
                    Toast.makeText(MainActivity.this, "Server response is empty!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().isSuccess()){
                        startActivity(new Intent(MainActivity.this, Login.class));
                    }
                }
            }
            @Override
            public void onFailure(Call<UserAuthResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}