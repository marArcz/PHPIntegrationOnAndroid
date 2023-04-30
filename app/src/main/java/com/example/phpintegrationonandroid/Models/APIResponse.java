package com.example.phpintegrationonandroid.Models;

public class APIResponse {
    private boolean success;
    private String message;
    private int status_code;

    public APIResponse () {}

    public APIResponse(boolean success, String message, int status_code) {
        this.success = success;
        this.message = message;
        this.status_code = status_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
