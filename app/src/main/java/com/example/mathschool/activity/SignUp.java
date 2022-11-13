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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

      EditText firstName;
      EditText lastName;
      EditText email;
      EditText password;
      AppCompatButton buttonCreateAcount;
      FirebaseAuth mAuth;
      Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        inicializar();




    }

    private void inicializar() {
        firstName = findViewById(R.id.edit_firstnameInputSignUp);
        lastName = findViewById(R.id.edit_lastnameInputSignUp);
        email = findViewById(R.id.edit_emailInputSignUp);
        password = findViewById(R.id.edit_passwordInputSignUp);
        buttonCreateAcount = findViewById(R.id.button_createAccountSignup);
        mAuth = FirebaseAuth.getInstance();
    }

    public void validarCampos( View v){
        String firstname = firstName.getText().toString();
        String lastname = lastName.getText().toString();
        String inEmail = email.getText().toString();
        String inPassword = password.getText().toString();

        if(!firstname.isEmpty()){
                if(!lastname.isEmpty()){
                        if(!inEmail.isEmpty()){
                            if(!inPassword.isEmpty()){

                                usuario = new Usuario();
                                usuario.setFirstName(firstname);
                                usuario.setLastName(lastname);
                                usuario.setEmail(inEmail);
                                usuario.setPassword(inPassword);

                                cadastrarUsuario();

                            }else{
                                Toast.makeText(this, "Preencha sua senha.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "Preencha seu E-mail.", Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Toast.makeText(this, "Preencha seu segundo nome.", Toast.LENGTH_SHORT).show();
                }
        }else{
            Toast.makeText(this, "Preencha seu primeiro nome.", Toast.LENGTH_SHORT).show();
        }



    }

    private void cadastrarUsuario() {

        mAuth = ConfigBD.FirebaseAuth();
        mAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Sucesso ao cadastrar o usuário.",Toast.LENGTH_LONG).show();
                }else{
                    String excecao= "";

                    try {
                            throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um E-mail válido.";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Essa conta já existe.";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário." + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(SignUp.this, excecao,Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}