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

import com.example.phpintegrationonandroid.Models.APIResponse;
import com.example.phpintegrationonandroid.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    Button btnEdit, btnChangePassword, btnLogout,btnDelete;
    EditText editTextFirstname, editTextMiddlename, editTextLastname,editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editTextFirstname = findViewById(R.id.edit_text_firstname);
        editTextLastname = findViewById(R.id.edit_text_lastname);
        editTextMiddlename = findViewById(R.id.edit_text_middlename);
        editTextEmail = findViewById(R.id.edit_text_email);

        btnDelete = findViewById(R.id.btn_delete);
        btnEdit = findViewById(R.id.btn_save);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnLogout = findViewById(R.id.btn_log_out);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteBtnClicked();
            }
        });

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

    private void onDeleteBtnClicked() {
        User user = AppManager.getUser(this);
        Call<APIResponse> deleteUser = RetrofitClient.getAccountAPI().deleteUser(user.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setMessage("Do you really to delete your account?");
        builder.setTitle("Confirm Action");
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            deleteUser.enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    if(response.body() == null){
                        Toast.makeText(AccountActivity.this, "Server response is empty!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AccountActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if(response.body().isSuccess()){
                            Toast.makeText(AccountActivity.this, "We successfully deleted your account", Toast.LENGTH_SHORT).show();
                            AppManager.clearPreferences(getApplicationContext());
                            startActivity(new Intent(AccountActivity.this, Login.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    Toast.makeText(AccountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        });

        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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