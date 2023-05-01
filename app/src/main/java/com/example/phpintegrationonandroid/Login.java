package com.example.phpintegrationonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phpintegrationonandroid.Models.UserAuthResponse;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btnLogin,btnSignup;
    EditText editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_firstname);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLoginClicked();
            }
        });

        btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }

    private void onBtnLoginClicked(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "You need to fill all the fields!", Toast.LENGTH_SHORT).show();

        }else{
            Call<UserAuthResponse> loginCall = RetrofitClient.getAccountAPI().login(email,password);

            loginCall.enqueue(new Callback<UserAuthResponse>() {
                @Override
                public void onResponse(Call<UserAuthResponse> call, Response<UserAuthResponse> response) {
                    if(response.body() == null){
                        Toast.makeText(Login.this, "Server Response is empty!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if(response.body().isSuccess()){
                            AppManager.saveUser(response.body().getUser(),getApplicationContext());
                            startActivity(new Intent(Login.this, AccountActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserAuthResponse> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }
    }

}