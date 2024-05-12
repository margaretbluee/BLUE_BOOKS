package com.example.blue_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
public class Dashb_ClientsFragment extends Fragment implements AdapterListenerFBClients {



    public Dashb_ClientsFragment() {
     }

RecyclerView recviewfbcdash;
    FBClients_RecyclerViewAdapter fbclients_recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashb__clients, container, false);

        recviewfbcdash =  view.findViewById(R.id.dashClientsListIdfb);



        getActivity().setTitle("ΠΕΛΑΤΕΣ");


        List<FB_Clients> fb_clients = new ArrayList<>();


         fbclients_recyclerViewAdapter = new FBClients_RecyclerViewAdapter(getContext(), this, fb_clients);
        recviewfbcdash.setAdapter(fbclients_recyclerViewAdapter);
        recviewfbcdash.setLayoutManager(new LinearLayoutManager(getContext()));
        fbclients_recyclerViewAdapter.showItems(fb_clients);

return  view;
    }

    @Override
    public void OnUpdateFBC(FB_Clients fb_clients ,int pos){

        Intent intent=new Intent(getContext(),edit_fbclient.class);
        intent.putExtra("model",fb_clients);
        startActivity(intent);}
    @Override
    public void OnDeleteFBC(FB_Clients fb_clients, int id, int pos) {
        List<FB_Clients> fbclientsList;
        try{

            CollectionReference collectionReference= MainActivity.fbdb.collection("FB_Clients");
            collectionReference.document(String.valueOf(id)).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();

                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
                        }
                    });
            recviewfbcdash.removeViewAt(pos);
            fbclients_recyclerViewAdapter.notifyItemRemoved(pos);

            fbclients_recyclerViewAdapter.removeItems(pos);



            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
        }}




    @Override
    public void onResume(){
        super.onResume();
        fbclients_recyclerViewAdapter.clearData();
        List<FB_Clients> fb_clients = new ArrayList<>();
        CollectionReference collectionReference =     MainActivity
                .fbdb.collection("FB_Clients");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    FB_Clients fb_clients1 = documentSnapshot.toObject(FB_Clients.class);

                    fbclients_recyclerViewAdapter.addfbClients(fb_clients1);
                }       }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
            }
        });

        //    fbitems_recyclerViewAdapter.clearData();
        fbclients_recyclerViewAdapter = new FBClients_RecyclerViewAdapter(getContext(), this, fb_clients);
        recviewfbcdash.setAdapter(fbclients_recyclerViewAdapter);
        recviewfbcdash.setLayoutManager(new LinearLayoutManager(getContext()));
        fbclients_recyclerViewAdapter.showItems(fb_clients);

    }


}