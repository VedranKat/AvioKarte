package com.example.aviokarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText username, password1, password2;
    Button register;
    FirebaseAuth mAuth;
    String uEmail, uPassword1, uPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.registerFinalButton);
        username = findViewById(R.id.usernameRegister);
        password1 = findViewById(R.id.etPassword1);
        password2 = findViewById(R.id.etPassword2);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uEmail = username.getText().toString();
                uPassword1 = password1.getText().toString();
                uPassword2 = password2.getText().toString();

                if (uPassword1.equals(uPassword2)){
                    if(uPassword1.length() < 6){
                        Toast.makeText(getApplicationContext(), "Lozinka mora sadržavati 6 znakova", Toast.LENGTH_SHORT).show();
                    }else{
                        createAccount(uEmail, uPassword1);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Lozinke se ne podudaraju", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Register.this, MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}