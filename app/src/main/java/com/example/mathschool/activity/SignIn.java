package com.example.mathschool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.mathschool.API.ApiClient;
import com.example.mathschool.API.SignInRequest;
import com.example.mathschool.API.SignInResponse;
import com.example.mathschool.R;
import com.example.mathschool.modelo.Usuario;
import com.example.mathschool.utils.ConfigBD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

     AppCompatButton button_signup;
     AppCompatButton button_signin;
     EditText inemail;
     EditText inpassword;
     FirebaseAuth mAuth;
     Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        mAuth = ConfigBD.FirebaseAuth();
        inicializar();




        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });




    }

    private void inicializar() {

        button_signup = findViewById(R.id.button_singUp);
        button_signin = findViewById(R.id.button_signin);
        inemail= findViewById(R.id.edit_email);
        inpassword= findViewById(R.id.edit_password);


    }




      public void validarAuthenticacao( View v){
        String email = inemail.getText().toString();
        String password = inpassword.getText().toString();
        
        
        if(!email.isEmpty()){
            if(!password.isEmpty()){

                    /*usuario = new Usuario();
                    usuario.setEmail(inEmail);
                    usuario.setPassword(inPassword);
                    login(usuario);*/

                SignInRequest signInRequest = new SignInRequest();

                signInRequest.setEmail(email);
                signInRequest.setPassword(password);
                LoginUser(signInRequest);



            }else{
                Toast.makeText(this, "Digite sua Senha.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Digite seu E-mail. ", Toast.LENGTH_SHORT).show();
        }
      }


    //Função de SignIn de usuario utilizando o Firebase Auth
    private void login(Usuario usuario) {

        mAuth.signInWithEmailAndPassword(
                usuario.getEmail(),usuario.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                openHome();
            }else{
                String excecaao= "";

                try{
                    throw task.getException();
                }catch (FirebaseAuthInvalidUserException e){
                    excecaao = "Usuário não está cadastrado.";
                }catch (FirebaseAuthInvalidCredentialsException e){
                    excecaao = "E-mail ou Senha incorretos.";
                }catch (Exception e){
                    excecaao = "Erro ao Logar." + e.getMessage();
                    e.printStackTrace();
                }
                Toast.makeText(SignIn.this, excecaao, Toast.LENGTH_SHORT).show();
            }


            }
        });


    }

    private void openHome() {
        startActivity(new Intent(SignIn.this,Home.class));
    }
    @Override
    protected void onStart() {
    super.onStart();
    FirebaseUser userAuth = mAuth.getCurrentUser();
    if(userAuth != null){
        openHome();
    }
    }


    //Função para registrar usuario com a RestAPI

   public void LoginUser(SignInRequest signInRequest){
            Call<SignInResponse> loginResponseCall = ApiClient.getService().loginUser(signInRequest);
        loginResponseCall.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {

                if(response.isSuccessful()){
                    SignInResponse signInResponse = response.body();
                        startActivity(new Intent(SignIn.this,Home.class).putExtra("data",signInResponse));
                        finish();

                }else{
                    String massage="Não foi possível realizar Login.";
                    Toast.makeText(SignIn.this,massage,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                String massage=t.getLocalizedMessage();
                Toast.makeText(SignIn.this,massage,Toast.LENGTH_LONG).show();
            }
        });
   }


}
