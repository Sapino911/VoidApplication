package com.example.voidapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voidapplication.Adapter.RecyclerAdapter;
import com.example.voidapplication.Model.Collection;
import com.example.voidapplication.R;
import com.example.voidapplication.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectionAccount extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    //private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Collection> mCollections;

    private FirebaseUser user;
    private DatabaseReference reference, mDatabaseRef;
    private String userID;

    private ImageButton add_category, btnAdd;
    private Button  logout;

    private ProgressBar progressBar;

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, CollectionDetailsActivity.class);
        intent.putExtra("NAME_KEY",data[0]);
        intent.putExtra("DESCRIPTION_KEY",data[1]);
        intent.putExtra("IMAGE_KEY",data[2]);
        startActivity(intent);
    }

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

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        mCollections = new ArrayList<>();
        mAdapter = new RecyclerAdapter (CollectionAccount.this, mCollections);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CollectionAccount.this);

        //User
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.name);

       // mStorage = FirebaseStorage.getInstance();
        //reference = FirebaseDatabase.getInstance().getReference("collections_uploads");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
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

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("collections_uploads");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mCollections.clear();

                for (DataSnapshot collectionSnapshot : dataSnapshot.getChildren()) {
                    Collection upload = collectionSnapshot.getValue(Collection.class);
                    if(upload != null){
                        //assert upload != null;
                        upload.setKey(collectionSnapshot.getKey());
                        mCollections.add(upload);
                    }
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CollectionAccount.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onItemClick(int position) {
        Collection clickedCollection=mCollections.get(position);
        String[] collectionData={clickedCollection.getName(),clickedCollection.getDescription(),clickedCollection.getImageUrl()};
        openDetailActivity(collectionData);
    }

    @Override
    public void onShowItemClick(int position) {
        Collection clickedCollection=mCollections.get(position);
        String[] collectionData={clickedCollection.getName(),clickedCollection.getDescription(),clickedCollection.getImageUrl()};
        openDetailActivity(collectionData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        Collection selectedItem = mCollections.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(CollectionAccount.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

   public void openAdd_Collection() {
        AddCollectionActivity collectionDialog = new AddCollectionActivity();
        collectionDialog.show(getSupportFragmentManager(), "Add Collection");
    }
}