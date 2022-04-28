package com.example.voidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Create_Account extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ImageButton btncirlcecreate;
    private EditText textName, textEmail, textPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        btncirlcecreate = (ImageButton) findViewById(R.id.btncirlcecreate);
        btncirlcecreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Create_Account(); }
        });

        textName = (EditText) findViewById(R.id.name);
        textEmail = (EditText) findViewById(R.id.email);
        textPassword = (EditText) findViewById(R.id.password);

    }

    private void Create_Account() {
        /*Intent intent = new Intent(this, Create_Account.class);
        startActivity(intent);*/
        String name = textName.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(name.isEmpty()){
            textName.setError("Full name is required!");
            textName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            textEmail.setError("Email is required!");
            textEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Please enter a valid email address!");
            textEmail.requestFocus();
        }

        if(password.isEmpty()){
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            textPassword.setError("Password should be atleast 6 characters!");
            textPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(name, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Create_Account.this, "Registration Succesful!", Toast.LENGTH_LONG).show();

                                    } else{
                                        Toast.makeText(Create_Account.this, "Registration Failed! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Create_Account.this, "Failed to create account", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}