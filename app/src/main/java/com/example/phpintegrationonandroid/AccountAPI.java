package com.example.phpintegrationonandroid;

import com.example.phpintegrationonandroid.Models.User;
import com.example.phpintegrationonandroid.Models.UserAuthResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountAPI {
    @POST("users/signup.php")
    Call<UserAuthResponse> register(@Body User user);

    @POST("users/login.php")
    Call<UserAuthResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
