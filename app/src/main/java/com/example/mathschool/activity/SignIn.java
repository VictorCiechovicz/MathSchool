package com.example.mathschool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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

public class SignIn extends AppCompatActivity {

     AppCompatButton button_signup;
     AppCompatButton button_signin;
     EditText email;
     EditText password;
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
        email= findViewById(R.id.edit_email);
        password= findViewById(R.id.edit_password);


    }


   //requisição a api




      public void validarAuthenticacao( View v){
        String inEmail = email.getText().toString();
        String inPassword = password.getText().toString();
        
        
        if(!inEmail.isEmpty()){
            if(!inPassword.isEmpty()){

                    usuario = new Usuario();

                    usuario.setEmail(inEmail);
                    usuario.setPassword(inPassword);

                    login(usuario);

            }else{
                Toast.makeText(this, "Digite sua Senha.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Digite seu E-mail. ", Toast.LENGTH_SHORT).show();
        }
      }

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

}
