package com.tawa.tawa_app.specialists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tawa.tawa_app.R;

import com.tawa.tawa_app.model.Specialist;



public class SpecialistsFragment extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("specialists");
    private SpecialistAdapter adapter;

    public static SpecialistsFragment newInstance() {
        return new SpecialistsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specialists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text =getActivity().findViewById(R.id.toolbar_title);
        text.setText(getArguments().getString("speciality")+"/"+getArguments().getString("region"));

        Query query = notebookRef
         .whereEqualTo("speciality",getArguments().getString("speciality")).whereEqualTo("region",getArguments().getString("region"));
                //.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Specialist> options = new FirestoreRecyclerOptions.Builder<Specialist>()
                .setQuery(query, Specialist.class)
                .build();

        adapter = new SpecialistAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.specialist_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListner(new SpecialistAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Specialist specialist = documentSnapshot.toObject(Specialist.class);
                String id = documentSnapshot.getId();

             //   Navigation.findNavController(getView()).navigate(R.id.action_regionFragment_to_specialitiesFragment,null);

              //     Toast.makeText(getContext(), "position" + position+"id"+ id, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }
}