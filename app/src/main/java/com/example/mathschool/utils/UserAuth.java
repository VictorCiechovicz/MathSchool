package com.example.mathschool.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuth {

public static FirebaseUser userLog(){
    FirebaseAuth usuario = ConfigBD.FirebaseAuth();
    return usuario.getCurrentUser();

}

}
