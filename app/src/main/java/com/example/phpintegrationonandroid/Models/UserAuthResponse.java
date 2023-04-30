package com.example.phpintegrationonandroid.Models;

import androidx.annotation.Nullable;

public class UserAuthResponse extends APIResponse{
    @Nullable
    private User user = null;

    public UserAuthResponse() {

    }

    public UserAuthResponse(boolean isSuccess, String message, int statusCode, User user) {
        super(isSuccess, message, statusCode);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
