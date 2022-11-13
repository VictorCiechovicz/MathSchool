package com.example.mathschool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mathschool.R;

public class NavBar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        getSupportActionBar().hide();
    }
}