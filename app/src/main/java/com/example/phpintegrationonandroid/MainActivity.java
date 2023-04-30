package com.example.phpintegrationonandroid;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phpintegrationonandroid.Models.User;
import com.example.phpintegrationonandroid.Models.UserAuthResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnSave;
    private EditText etFirstName,etMiddleName,etLastName,etEmail,etPassword,etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize objects;

        btnSave = findViewById(R.id.btnRegister);
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              OnSignUp();
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
                if(response.body().isSuccess()){
                    showSnackbar(response.body().getMessage(), btnSave);
                    AppManager.saveUser(response.body().getUser(),getApplicationContext());
                }else{
                    showSnackbar(response.message(), btnSave);
                }
            }
            @Override
            public void onFailure(Call<UserAuthResponse> call, Throwable t) {
                t.printStackTrace();
                showSnackbar(  t.getMessage(),btnSave);
            }
        });

    }
    private void showSnackbar(String message,View view){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}