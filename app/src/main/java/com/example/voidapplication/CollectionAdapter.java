package com.example.voidapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voidapplication.databinding.RecyclerViewCollectionBinding;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    //var collections = mutableListOf<Collection>()

    @NotNull
    private final List collections = (List)(new ArrayList());

   /*@NotNull
    public final List getCollections() {
        return this.collections;
    }

    public final void setCollections(@NotNull List Collection) {
        //Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.collections = Collection;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewCollectionBinding view = RecyclerViewCollectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerViewCollectionBinding binding;

        public ViewHolder(@NonNull RecyclerViewCollectionBinding binding) {
            super((View)binding.getRoot());
            this.binding = binding;
        }
    }
}
