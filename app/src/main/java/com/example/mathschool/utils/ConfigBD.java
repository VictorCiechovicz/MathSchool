package com.example.mathschool.utils;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigBD {

    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

}
