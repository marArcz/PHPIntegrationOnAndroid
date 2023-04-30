package com.example.phpintegrationonandroid;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.phpintegrationonandroid.Models.User;

public class AppManager {
    public static final String SHARED_PREFERENCES_NAME = "android_retrofit_shared_preferences";

    public static void saveUser(User user, Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("firstname", user.getFirstname());
        editor.putString("middlename", user.getMiddlename());
        editor.putString("lastname", user.getLastname());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());

        editor.commit();
    }

    public static void clearPreferences(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.clear();
        editor.commit();
    }
}
