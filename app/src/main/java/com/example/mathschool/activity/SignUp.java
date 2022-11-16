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
import com.example.mathschool.API.SignUpRequest;
import com.example.mathschool.API.SignUpResponse;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

      EditText infirstName;
      EditText inlastName;
      EditText inemail;
      EditText inpassword;
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
        infirstName = findViewById(R.id.edit_firstnameInputSignUp);
        inlastName = findViewById(R.id.edit_lastnameInputSignUp);
        inemail = findViewById(R.id.edit_emailInputSignUp);
        inpassword = findViewById(R.id.edit_passwordInputSignUp);
        buttonCreateAcount = findViewById(R.id.button_createAccountSignup);
        mAuth = FirebaseAuth.getInstance();
    }




    public void validarCampos( View v){
        String firstName = infirstName.getText().toString();
        String lastName = inlastName.getText().toString();
        String email = inemail.getText().toString();
        String password = inpassword.getText().toString();

        if(!firstName.isEmpty()){
                if(!lastName.isEmpty()){
                        if(!email.isEmpty()){
                            if(!password.isEmpty()){

                                /*usuario = new Usuario();
                                usuario.setFirstName(firstname);
                                usuario.setLastName(lastname);
                                usuario.setEmail(inEmail);
                                usuario.setPassword(inPassword);
                                cadastrarUsuario();*/

                                SignUpRequest signUpRequest= new SignUpRequest();

                                signUpRequest.setFirstName(firstName);
                                signUpRequest.setLastname(lastName);
                                signUpRequest.setEmail(email);
                                signUpRequest.setPassword(password);
                                registerUser(signUpRequest);


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

    //Registrando usuário utilizando o FirebaseAuth

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

    //Registrando usuario com a API

    public void registerUser(SignUpRequest signUpRequest){
        Call<SignUpResponse> signUpResponseCall = ApiClient.getService().registerUser(signUpRequest);
        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                if(response.isSuccessful()){


                    String massage="Cadastrado com sucesso.";
                    Toast.makeText(SignUp.this,massage,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(SignUp.this,SignIn.class));
                    finish();
                }else{
                    String massage="Não foi possível o Cadastro.";
                    Toast.makeText(SignUp.this,massage,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                String massage=t.getLocalizedMessage();
                Toast.makeText(SignUp.this,massage,Toast.LENGTH_LONG).show();
            }
        });
    }








}