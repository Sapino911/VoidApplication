package com.example.voidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CollectionAccount extends AppCompatActivity {
    private ImageButton btncirlceadd;

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
    }

    public void openAdd_Collection() {
        AddCollectionDialog collectionDialog = new AddCollectionDialog();
        collectionDialog.show(getSupportFragmentManager(), "Add Collection");
    }
}