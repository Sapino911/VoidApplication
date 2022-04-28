package com.example.voidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //create Variables
    private EditText textEmail, textPassword;
    private ImageButton btncirlcelogin;
    private Button signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Signup Button
        signup = (Button) findViewById(R.id.signup);
        //Cast to button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate_Account();
            }
        });

        //Initialize Login Button
        btncirlcelogin = (ImageButton) findViewById(R.id.btncirlcelogin);
        btncirlcelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        textEmail = (EditText) findViewById(R.id.email);
        textPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
    }

    //Method
    public void openCreate_Account(){
        Intent intent = new Intent(this, Create_Account.class);
        startActivity(intent);
    }

    private void userLogin(){
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(email.isEmpty()){
            textEmail.setError("Email is required");
            textEmail.requestFocus();
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile
                    Intent intent = new Intent(MainActivity.this, CollectionAccount.class);
                     startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}