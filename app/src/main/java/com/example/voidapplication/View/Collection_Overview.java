package com.example.voidapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.voidapplication.R;

import java.util.ArrayList;

public class Collection_Overview extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_overview);

        listView = findViewById(R.id.listView);

        myList =  new ArrayList<>();
        myList.add("13 Assassins");
        myList.add("6 Underground");

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(adapter);
    }
}