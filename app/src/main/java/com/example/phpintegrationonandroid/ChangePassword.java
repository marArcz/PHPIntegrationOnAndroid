package com.example.phpintegrationonandroid;

import androidx.appcompat.app.AppCompatActivity;

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

public class ChangePassword extends AppCompatActivity {
    Button btnSave,btnCancel;
    EditText editTextCurrentPass, editTextNewPass,editTextConfirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextCurrentPass = findViewById(R.id.edit_text_current_password);
        editTextNewPass = findViewById(R.id.edit_text_new_password);
        editTextConfirmPass = findViewById(R.id.edit_text_confirm_password);

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


    }

    private void onBtnSaveClicked(){
        int id = AppManager.getUser(this).getId();
        String currentPass = editTextCurrentPass.getText().toString();
        String newPass = editTextNewPass.getText().toString();
        String confirmPass = editTextConfirmPass.getText().toString();

        if(newPass.equals(confirmPass)){
            Call<UserAuthResponse> changePassword = RetrofitClient.getAccountAPI().changePassword(id, currentPass,newPass,confirmPass);
            changePassword.enqueue(new Callback<UserAuthResponse>() {
                @Override
                public void onResponse(Call<UserAuthResponse> call, Response<UserAuthResponse> response) {
                    if(response.body() == null){
                        Toast.makeText(ChangePassword.this, "Server response is empty!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if(response.body().isSuccess()){
                            if(AppManager.saveUser(response.body().getUser(),getApplicationContext())){
                                finish();
                            }else{
                                Toast.makeText(ChangePassword.this, "Can't save user!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserAuthResponse> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    t.printStackTrace();
                }
            });
        }else{
            Toast.makeText(ChangePassword.this, "Your passwords does not match!", Toast.LENGTH_SHORT).show();

        }
    }

}