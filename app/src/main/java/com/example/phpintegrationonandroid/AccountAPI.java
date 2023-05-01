package com.example.phpintegrationonandroid;

import com.example.phpintegrationonandroid.Models.User;
import com.example.phpintegrationonandroid.Models.UserAuthResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountAPI {
    @POST("users/signup.php")
    Call<UserAuthResponse> register(@Body User user);

    @FormUrlEncoded
    @POST("users/login.php")
    Call<UserAuthResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("users/update.php")
    Call<UserAuthResponse> updateUser(@Body User user);

    @POST("users/delete.php")
    Call<Response> deleteUser(@Field("id") int userId);

    @FormUrlEncoded
    @POST("users/change-password.php")
    Call<UserAuthResponse> changePassword(
            @Field("id") int userId,
            @Field("current_password") String currentPassword,
            @Field("new_password") String newPassword,
            @Field("confirm_password") String confirmPassword
    );
}
