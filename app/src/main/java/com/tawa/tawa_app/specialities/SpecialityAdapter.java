package com.tawa.tawa_app.specialities;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tawa.tawa_app.R;
import com.tawa.tawa_app.model.Region;

class SpecialityAdapter extends FirestoreRecyclerAdapter<Region, SpecialityAdapter.NoteHolder> {

    private onItemClickListener listener;

    public SpecialityAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Region model) {
        holder.textView.setText(model.getName());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_item, parent, false);
        return new NoteHolder(v);
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public NoteHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_region);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListner(onItemClickListener listner) {
        this.listener=listner;
    }
}
