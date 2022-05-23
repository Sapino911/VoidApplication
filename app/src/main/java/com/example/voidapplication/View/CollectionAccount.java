package com.example.voidapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voidapplication.R;
import com.example.voidapplication.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollectionAccount extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private ImageButton add_category, btnAdd;
    private Button  logout;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_account);

        add_category = (ImageButton) findViewById(R.id.add_category);
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd_Collection();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /*add_category = (ImageButton) findViewById(R.id.add_category);
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (CollectionAccount.this, AddCollectionActivity.class);
                startActivity(intent);
            }
        });*/

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (CollectionAccount.this, MainActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.name);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Create User Object
                //Get value from User Class (e.g.fullName)
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.name;

                    fullNameTextView.setText(" " + fullName.toUpperCase());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CollectionAccount.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

   public void openAdd_Collection() {
        AddCollectionActivity collectionDialog = new AddCollectionActivity();
        collectionDialog.show(getSupportFragmentManager(), "Add Collection");
    }
}