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

public class SignIn extends AppCompatActivity {

    private AppCompatButton button_signup;
    private AppCompatButton button_signin;
    private EditText inEmail;
    private EditText inPassword;
    private FirebaseAuth mAuth;
    private Usuario u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();




         button_signup = findViewById(R.id.appCompatButtonSingUp);
         button_signin = findViewById(R.id.button_signin);
         inEmail= findViewById(R.id.edit_email);
         inPassword= findViewById(R.id.edit_password);


        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receberdados();
                logar();
            }
        });


    }

    private void receberdados() {
    u = new Usuario();
    u.setEmail(inEmail.getText().toString());
    u.setPassword(inPassword.getText().toString());



    }

    private void logar() {
      mAuth.signInWithEmailAndPassword(u.getEmail(),u.getPassword())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(SignIn.this,Home.class));
                }else{
                    Toast.makeText(SignIn.this, "Não foi possível realizar Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
