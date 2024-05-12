package com.example.blue_books;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Dashb_SalesFragment extends Fragment implements AdapterListenerFBSales {

    public Dashb_SalesFragment(){}
    private FBSales_RecyclerViewAdapter fbsales_recyclerViewAdapter;
    List<FB_Sales> sales;
    RecyclerView recviewfbs;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashb_fbsales, container, false);

        recviewfbs = view.findViewById(R.id.dashSalesListIdfb);

        FB_Sales fbsales = new FB_Sales();

        List<FB_Sales> fb_sales = new ArrayList<>();


        fbsales_recyclerViewAdapter = new FBSales_RecyclerViewAdapter(getContext(), this, fb_sales);
        recviewfbs.setAdapter(fbsales_recyclerViewAdapter);
        recviewfbs.setLayoutManager(new LinearLayoutManager(getContext()));
        fbsales_recyclerViewAdapter.showSales(fb_sales);

        recviewfbs.invalidate();

         return  view;
    }

    @Override
    public void OnUpdateFBS(FB_Sales fb_sales, int pos) {

    }

    @Override
        public void OnDeleteFBS(FB_Sales fb_sales,  int id, int pos) {
            List<FB_Sales> fbsalesList;
            try{

                CollectionReference collectionReference= MainActivity.fbdb.collection("FB_Sales");
                collectionReference.document().delete()
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
                recviewfbs.removeViewAt(pos);
                fbsales_recyclerViewAdapter.notifyItemRemoved(pos);

                fbsales_recyclerViewAdapter.removeSales(pos);

                Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();
            }catch(Exception e){
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
            }
        }




        @Override
        public void onResume(){
            super.onResume();
            fbsales_recyclerViewAdapter.clearData();
            List<FB_Sales> fb_sales = new ArrayList<>();
            CollectionReference collectionReference =     MainActivity
                    .fbdb.collection("FB_Sales");
            collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                        FB_Sales fb_sales1 = documentSnapshot.toObject(FB_Sales.class);
                        Integer  id = fb_sales1.getFb_sales_id();
                        DocumentReference iid= fb_sales1.getFb_item_id();

                        DocumentReference cid= fb_sales1.getFb_client_id();

                        fbsales_recyclerViewAdapter.addfbSales(fb_sales1);
                    }       }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
                }
            });

            //    fbitems_recyclerViewAdapter.clearData();
            fbsales_recyclerViewAdapter = new FBSales_RecyclerViewAdapter(getContext(), this, fb_sales);
            recviewfbs.setAdapter(fbsales_recyclerViewAdapter);
            recviewfbs.setLayoutManager(new LinearLayoutManager(getContext()));
            fbsales_recyclerViewAdapter.showSales(fb_sales);

        }}

