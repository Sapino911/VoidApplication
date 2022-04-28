package com.example.voidapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AddCollectionDialog extends AppCompatDialogFragment {
    private EditText ctitle;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_collection, null);

        builder.setView(view)
                .setTitle("Add New Collection")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        ctitle = view.findViewById(R.id.ctitle);
        return builder.create();
    }
}
