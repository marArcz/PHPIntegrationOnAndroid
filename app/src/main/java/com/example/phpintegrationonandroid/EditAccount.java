package com.example.phpintegrationonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class EditAccount extends AppCompatActivity {
    Button btnSave, btnCancel;
    EditText editTextFirstname, editTextMiddlename, editTextLastname,editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        editTextFirstname = findViewById(R.id.edit_text_firstname);
        editTextLastname = findViewById(R.id.edit_text_lastname);
        editTextMiddlename = findViewById(R.id.edit_text_middlename);
        editTextEmail = findViewById(R.id.edit_text_email);

        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSaveClicked();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadUserInfo();
    }
    private void onBtnSaveClicked(){
        User user = AppManager.getUser(this);
        user.setFirstname(editTextFirstname.getText().toString());
        user.setMiddlename(editTextMiddlename.getText().toString());
        user.setLastname(editTextLastname.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        Call<UserAuthResponse> updateUserCall = RetrofitClient.getAccountAPI().updateUser(user);

        updateUserCall.enqueue(new Callback<UserAuthResponse>() {
            @Override
            public void onResponse(Call<UserAuthResponse> call, Response<UserAuthResponse> response) {
                if(response.body() == null){
                    Toast.makeText(EditAccount.this, "Server response is empty!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(EditAccount.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().isSuccess()){
                        boolean saved = AppManager.saveUser(response.body().getUser(),getApplicationContext());
                        if(saved){
                            finish();
                        }else{
                            Toast.makeText(EditAccount.this, "Cannot save user!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserAuthResponse> call, Throwable t) {
                Toast.makeText(EditAccount.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void loadUserInfo(){
        User user = AppManager.getUser(this);

        if(user == null){
            //if not logged in
            startActivity(new Intent(EditAccount.this, Login.class));
            finish();
        }else{
            editTextFirstname.setText(user.getFirstname());
            editTextMiddlename.setText(user.getMiddlename());
            editTextLastname.setText(user.getLastname());
            editTextEmail.setText(user.getEmail());
        }
    }


}