package com.example.mathschool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mathschool.R;
import com.example.mathschool.utils.ConfigBD;
import com.google.firebase.auth.FirebaseAuth;

public class NavBar extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        getSupportActionBar().hide();

        mAuth = ConfigBD.FirebaseAuth();
    }

    public void logout(View v){
            try{
                mAuth.signOut();
                finish();
            }catch ( Exception e){
                e.printStackTrace();
            }
    }




}