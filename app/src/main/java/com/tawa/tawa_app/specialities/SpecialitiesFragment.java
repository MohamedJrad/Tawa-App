package com.tawa.tawa_app.specialities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tawa.tawa_app.R;
import com.tawa.tawa_app.model.Region;

import com.tawa.tawa_app.model.Speciality;
import com.tawa.tawa_app.regions.RegionViewModel;


public class SpecialitiesFragment extends Fragment {



    private RegionViewModel mViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("specialities");
    private SpecialityAdapter adapter;

    public static SpecialitiesFragment newInstance() {
        return new SpecialitiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.region_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       TextView text =getActivity().findViewById(R.id.toolbar_title);
     //  text.setText("sdafasdf");
        text.setText(getArguments().getString("region"));
      //  getActivity().setTitle("الاختصاصات");


        Query query = notebookRef.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Region> options = new FirestoreRecyclerOptions.Builder<Region>()
                .setQuery(query, Region.class)
                .build();

        adapter = new SpecialityAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.region_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListner(new SpecialityAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Speciality speciality = documentSnapshot.toObject(Speciality.class);
                String id = documentSnapshot.getId();
                Bundle bundle = new Bundle();
                bundle.putString("region", getArguments().getString("region"));
                bundle.putString("speciality",speciality.getName());


                Navigation.findNavController(getView()).navigate(R.id.action_specialitiesFragment_to_specialistsFragment,bundle);

                //   Toast.makeText(getContext(), "position" + position+"id"+ id, Toast.LENGTH_SHORT).show();
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
