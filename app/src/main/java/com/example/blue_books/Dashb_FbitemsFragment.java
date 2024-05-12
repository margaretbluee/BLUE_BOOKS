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
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Dashb_FbitemsFragment extends Fragment implements AdapterListenerFBItems{

    public Dashb_FbitemsFragment(){ }

    RangeSlider rangeSlider;
    RangeSlider range_posotita;
    RecyclerView recviewfbit;
   FBItems_RecyclerViewAdapter fbitems_recyclerViewAdapter;
   AdapterListenerFBItems adapterListenerFBItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashb_fbitems, container, false);
recviewfbit=view.findViewById(R.id.dashFbitemsListIdfb);
        List<FB_Items> fb_items = new ArrayList<>();


        fbitems_recyclerViewAdapter = new FBItems_RecyclerViewAdapter(getContext(), this, fb_items);
        recviewfbit.setAdapter(fbitems_recyclerViewAdapter);
        recviewfbit.setLayoutManager(new LinearLayoutManager(getContext()));
        fbitems_recyclerViewAdapter.showItems(fb_items);



        List<Integer> fbitems_id = new ArrayList<>();
        List<Integer> fbitems_price = new ArrayList<>();

        List<String> fbitems_names = new ArrayList<>();


rangeSlider=view.findViewById(R.id.slider);

 rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
     @Override
     public void onStartTrackingTouch(@androidx.annotation.NonNull RangeSlider slider) {
          List<Float> values = rangeSlider.getValues() ;
         float a = values.get(0);
         float b = values.get(1);
         //Toast.makeText(getActivity(),"a = "+a+"b="+b,Toast.LENGTH_LONG).show();
     }

     @Override
     public void onStopTrackingTouch(@androidx.annotation.NonNull RangeSlider slider) {
         List<Float> values = rangeSlider.getValues() ;
         float a = values.get(0);
         float b = values.get(1);


         fbitems_recyclerViewAdapter.clearData();
         List<FB_Items> fb_items = new ArrayList<>();
         CollectionReference collectionReference =     MainActivity
                 .fbdb.collection("FB_Items");
         collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                     FB_Items fb_items1 = documentSnapshot.toObject(FB_Items.class);
                     Integer  id = fb_items1.getFb_item_id();
                     String  name = fb_items1.getFb_item_name();
                     float rating = fb_items1.getFb_item_rating();
                     Integer price = fb_items1.getFb_item_price();
                     Integer count=fb_items1.getFb_item_count();
                     String  cat = fb_items1.getFb_item_cat();
if(price>=a && price <=b) {
    fbitems_recyclerViewAdapter.addfbItems(fb_items1);
}
                 }       }

         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
             }
         });

         //    fbitems_recyclerViewAdapter.clearData();
         fbitems_recyclerViewAdapter = new FBItems_RecyclerViewAdapter(getContext(),adapterListenerFBItems  , fb_items);
         recviewfbit.setAdapter(fbitems_recyclerViewAdapter);
         recviewfbit.setLayoutManager(new LinearLayoutManager(getContext()));
         fbitems_recyclerViewAdapter.showItems(fb_items);
                         }




 });



    return view;}



    @Override
    public void OnDeleteFBI(FB_Items fb_items, int id, int pos) {
        List<FB_Items> fbitemsList;
        try{

            CollectionReference collectionReference= MainActivity.fbdb.collection("FB_Items");
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
            recviewfbit.removeViewAt(pos);
            fbitems_recyclerViewAdapter.notifyItemRemoved(pos);

            fbitems_recyclerViewAdapter.removeItems(pos);



            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
        }}



    @Override
    public void onResume(){
        super.onResume();
      fbitems_recyclerViewAdapter.clearData();
        List<FB_Items> fb_items = new ArrayList<>();
        CollectionReference collectionReference =     MainActivity
                .fbdb.collection("FB_Items");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    FB_Items fb_items1 = documentSnapshot.toObject(FB_Items.class);
                    Integer  id = fb_items1.getFb_item_id();
                    String  name = fb_items1.getFb_item_name();
                    float rating = fb_items1.getFb_item_rating();
                    Integer price = fb_items1.getFb_item_price();
                    Integer count=fb_items1.getFb_item_count();
                    String  cat = fb_items1.getFb_item_cat();

                    fbitems_recyclerViewAdapter.addfbItems(fb_items1);
                }       }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
            }
        });

        //    fbitems_recyclerViewAdapter.clearData();
        fbitems_recyclerViewAdapter = new FBItems_RecyclerViewAdapter(getContext(), this, fb_items);
        recviewfbit.setAdapter(fbitems_recyclerViewAdapter);
        recviewfbit.setLayoutManager(new LinearLayoutManager(getContext()));
        fbitems_recyclerViewAdapter.showItems(fb_items);

    }

    @Override
    public void OnUpdateFBI(FB_Items fb_items,int pos){



        Intent intent=new Intent(getContext(),edit_fbitems.class);
        intent.putExtra("model",fb_items);
        startActivity(intent);
    }}
