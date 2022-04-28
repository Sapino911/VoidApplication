package com.example.voidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    //create Variable button
    private Button signup;
    private ImageButton btncirlcelogin;


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
                openCollection_Account();
            }
        });
    }

    //Method
    public void openCreate_Account(){
        Intent intent = new Intent(this, Create_Account.class);
        startActivity(intent);
    }

    public void openCollection_Account(){
        Intent intent = new Intent(this, CollectionAccount.class);
        startActivity(intent);
    }
}