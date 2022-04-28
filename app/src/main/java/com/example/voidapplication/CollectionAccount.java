package com.example.voidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class CollectionAccount extends AppCompatActivity {
    private ImageButton btncirlceadd;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_account);

        btncirlceadd = (ImageButton) findViewById(R.id.btncirlceadd);
        btncirlceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd_Collection();
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CollectionAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openAdd_Collection() {
        AddCollectionDialog collectionDialog = new AddCollectionDialog();
        collectionDialog.show(getSupportFragmentManager(), "Add Collection");
    }
}