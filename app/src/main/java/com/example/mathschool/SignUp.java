package com.example.mathschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mathschool.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private AppCompatButton buttonCreateAcount;
    private FirebaseAuth mAuth;
    private Usuario u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

    firstName = findViewById(R.id.edit_firstnameInputSignUp);
    lastName = findViewById(R.id.edit_lastnameInputSignUp);
    email = findViewById(R.id.edit_emailInputSignUp);
    password = findViewById(R.id.edit_passwordInputSignUp);
    buttonCreateAcount = findViewById(R.id.button_createAccountSignup);
    mAuth = FirebaseAuth.getInstance();



    buttonCreateAcount.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recuperarDado();
            criarlogin();


        }

        private void recuperarDado() {
            if(firstName.getText().toString()==""||lastName.getText().toString()==""||email.getText().toString()==""||password.getText().toString()==""){
                Toast.makeText(SignUp.this, "Você deve preencher todos os dados", Toast.LENGTH_SHORT).show();
            }else{
                u = new Usuario();
                u.setFirstName(firstName.getText().toString());
                u.setLastName(lastName.getText().toString());
                u.setEmail(email.getText().toString());
                u.setPassword(password.getText().toString());
            }
        }
    });




    }

    private void criarlogin() {
        mAuth.createUserWithEmailAndPassword(u.getEmail(),u.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            u.setId(user.getUid());
                            u.salvarDados();
                            startActivity(new Intent(SignUp.this,Home.class));
                        }else{
                            Toast.makeText(SignUp.this,"Erro ao criar o cadastro!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}