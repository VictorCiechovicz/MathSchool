package com.example.mathschool.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/create")
    Call<SignUpResponse> registerUser(@Body SignUpRequest signUpRequest);



}



