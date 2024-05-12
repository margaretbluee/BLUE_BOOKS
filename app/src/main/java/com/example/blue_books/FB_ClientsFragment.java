package com.example.blue_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FB_ClientsFragment extends Fragment  implements AdapterListenerFBClients {
    EditText fbcid, fbcname, fbccity , fbcrating;
    Button insert;
    private FBClients_RecyclerViewAdapter fbclients_recyclerViewAdapter;

    RecyclerView recviewfbc;

    Spinner fbspinnercd;
    List<FB_Clients> clients;

    public FB_ClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fb_clients, container, false);
        recviewfbc = view.findViewById(R.id.ClientsListIdfb);





List<FB_Clients> fb_clients = new ArrayList<>();


         fbclients_recyclerViewAdapter = new FBClients_RecyclerViewAdapter(getContext(), this, fb_clients);
        recviewfbc.setAdapter(fbclients_recyclerViewAdapter);
        recviewfbc.setLayoutManager(new LinearLayoutManager(getContext()));
        fbclients_recyclerViewAdapter.showItems(fb_clients);




        fbcid = view.findViewById(R.id.fbcid);
        fbcname = view.findViewById(R.id.fbcname);
         fbccity = view.findViewById(R.id.fbccity);
        fbcrating = view.findViewById(R.id.fbcrating);

        recviewfbc.invalidate();


        RatingBar simpleRatingBar=(RatingBar)view.findViewById(R.id.ratingBarfbc) ;

        simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
if(rating%2==0){                fbcrating.setText(String.valueOf(rating) );
}else {
  //  rating = rating + 1;
    fbcrating.setText(String.valueOf(rating) );
}

            }
        });


        insert = view.findViewById(R.id.bn_fbinsertc);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_clientid = 0;
                try {
                    Var_clientid = Integer.parseInt(fbcid.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_clientname = fbcname.getText().toString();
                float Var_clientrating = 0;
                try {
                    Var_clientrating = Float.parseFloat(String.valueOf(fbcrating.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }

                String Var_clientcity = fbccity.getText().toString();

                try {
                    FB_Clients fb_clients = new FB_Clients();

                    fb_clients.setFb_client_id(Var_clientid);
                    fb_clients.setFb_client_name(Var_clientname);
                     fb_clients.setFb_client_city(Var_clientcity);
                    fb_clients.setFb_client_rating(    Var_clientrating );

                    fbclients_recyclerViewAdapter.addfbClients(fb_clients);


                    MainActivity.fbdb.collection("FB_Clients")
                        .document(""+Var_clientid).
                        set(fb_clients).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "RECORD ADDED IN FIREBASE DB", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(),"add operation failed.",Toast.LENGTH_LONG).show();
                            }
                        });
            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            }

                        fbcid.setText("");
                        fbcname.setText("");
                         fbccity.setText("");
                        fbcrating.setText("");
        }

    });

        return view;
}


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
            recviewfbc.removeViewAt(pos);
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
                    Integer  cid = fb_clients1.getFb_client_id();
                    String  cname = fb_clients1.getFb_client_name();
                    Float crating = fb_clients1.getFb_client_rating();
                    String ccity = fb_clients1.getFb_client_city();

                    fbclients_recyclerViewAdapter.addfbClients(fb_clients1);
                }       }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_SHORT).show();
            }
        });

        //    fbitems_recyclerViewAdapter.clearData();
        fbclients_recyclerViewAdapter = new FBClients_RecyclerViewAdapter(getContext(), this, fb_clients);
        recviewfbc.setAdapter(fbclients_recyclerViewAdapter);
        recviewfbc.setLayoutManager(new LinearLayoutManager(getContext()));
        fbclients_recyclerViewAdapter.showItems(fb_clients);

    }

    @Override
    public void OnUpdateFBC(FB_Clients fb_clients ,int pos){

        Intent intent=new Intent(getContext(),edit_fbclient.class);
        intent.putExtra("model",fb_clients);
        startActivity(intent);}

}
