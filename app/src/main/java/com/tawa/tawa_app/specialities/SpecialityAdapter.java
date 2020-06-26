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
import com.tawa.tawa_app.model.Speciality;

class SpecialityAdapter extends FirestoreRecyclerAdapter<Speciality, SpecialityAdapter.SpecialityHolder> {

    private onItemClickListener listener;
    private onItemLongClickListener longClickListener;

    public SpecialityAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SpecialityHolder holder, int position, @NonNull Speciality model) {
        holder.textView.setText(model.getName());
    }

    @NonNull
    @Override
    public SpecialityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_item, parent, false);
        return new SpecialityHolder(v);
    }


    class SpecialityHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public SpecialityHolder(View itemView) {
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
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION  && longClickListener != null) {

                        longClickListener.onItemLongClick(getSnapshots().getSnapshot(position), position);
                    }
                    return false;
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
    public interface onItemLongClickListener {
        void onItemLongClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemLongClickListner(SpecialityAdapter.onItemLongClickListener listner) { this.longClickListener = listner; }
}
