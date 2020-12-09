package com.example.finalprojectstackoverflowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class DispatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);


        Class<?> activityClass;

        SharedPreferences prefs = getSharedPreferences(HomePageActivity.sharedName, MODE_PRIVATE);

        try {
            activityClass = Class.forName(prefs.getString(HomePageActivity.lastActivityKeyName, HomePageActivity.class.getName()));
        } catch (ClassNotFoundException ex) {
            activityClass = HomePageActivity.class;
        }

        startActivity(new Intent(this, activityClass));
    }
}
