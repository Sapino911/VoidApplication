package com.example.voidapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voidapplication.databinding.FragmentCollectionsBinding;
import com.google.firebase.database.annotations.NotNull;


public class CollectionsFragment extends Fragment {

    private FragmentCollectionsBinding _binding;

    private final FragmentCollectionsBinding getBinding() {

        return this._binding;
    }

    private final CollectionAdapter adapter = new CollectionAdapter();

    public CollectionsFragment() {
        // Required empty public constructor
    }


   /* public static CollectionsFragment newInstance(String param1, String param2) {
        CollectionsFragment fragment = new CollectionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        return (View)this.getBinding().getRoot();
        //return (View)this.getBinding().getRoot();
    }

    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView binding = this.getBinding().recyclerViewCollections;

        binding.setAdapter((RecyclerView.Adapter)this.adapter);

        /*this.getBinding().addButton.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                (new AddContactDialogFragment()).show(ContactsFragment.this.getChildFragmentManager(), "");
            }
        }));*/
    }
}