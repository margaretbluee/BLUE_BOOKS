package com.example.blue_books;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


class FBSales_RecyclerViewAdapter extends RecyclerView.Adapter<FBSales_RecyclerViewAdapter.MyViewHolder>{
    private List<FB_Sales> fbsalesList;
    private Context context;
    private AdapterListenerFBSales sfbadapterListener;

    public FBSales_RecyclerViewAdapter(Context context, AdapterListenerFBSales listener, List<FB_Sales> fbsalesList) {
        this.context = context;
        this.sfbadapterListener = listener;
        this.fbsalesList=fbsalesList;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_fbsales,parent,false);
        return new MyViewHolder(viewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {

    FB_Sales fbsales = fbsalesList.get(position);


        holder.sfbidTxt.setText(String.valueOf(fbsales.getFb_sales_id()));
        holder.sfbdateTxt.setText(fbsales.getFb_sales_date());
        holder.sfbcountTxt.setText(String.valueOf(fbsales.getFb_sales_count()));
        holder.sfbprofitTxt.setText(String.valueOf(fbsales.getFb_sales_profit()));
      
        holder.sfbiidTxt.setText(String.valueOf(fbsales.getFb_item_id()));
       holder.sfbcidTxt.setText(String.valueOf(fbsales.getFb_client_id()));


       DocumentReference iid=fbsales.getFb_item_id();


         MainActivity.fbdb.collection("FB_Sales")
                .document(String.valueOf(iid))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                 CollectionReference collectionReference =     MainActivity
                                                            .fbdb.collection("FB_Items" );

                                                    collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                                                FB_Items fb_items = documentSnapshot.toObject(FB_Items.class);

                                                                String fb_item_name = String.valueOf(fb_items.getFb_item_name());
                                                              String fb_item_id = String.valueOf(fb_items.getFb_item_id());
                                                  if(iid.getPath().endsWith(fb_item_id)    )       {
                                                                holder.sfbitnameTxt.setText(fb_item_name);
                                                                holder.sfbiidTxt.setText(String.valueOf(fb_item_id));}

                                                            }
                                                        }
                                                    }  );}});

        DocumentReference cid=  fbsales.getFb_client_id();
        MainActivity.fbdb.collection("FB_Sales")
                .document(String.valueOf(cid))
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        CollectionReference collectionReference =     MainActivity
                                .fbdb.collection("FB_Clients");

                        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                  FB_Clients fb_clients = documentSnapshot.toObject(FB_Clients.class);


                                    String fb_client_name = String.valueOf(fb_clients.getFb_client_name());
                                  String fb_client_id = String.valueOf(( fb_clients.getFb_client_id()));
if(cid.getPath().endsWith(fb_client_id)){
                                    holder.sfbclnameTxt.setText(fb_client_name);
                                    holder.sfbcidTxt.setText(String.valueOf(fb_client_id));}

                                }
                            }
                        }  );}});
        holder.imgDeleteFBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfbadapterListener.OnDeleteFBS(fbsales  ,  Integer.parseInt(String.valueOf(fbsales.getFb_sales_id())), holder.getAdapterPosition());
                //  removeItems( position );
            }
        });


    }


    @Override
    public int getItemCount() {
        return fbsalesList.size();
    }


    public void addfbSales(FB_Sales fb_sales) {
        fbsalesList.add(fb_sales);

        notifyItemRangeChanged(0, fbsalesList.size());

    }
    public void showSales(List<FB_Sales> newList) {
        if(newList!=fbsalesList)    {   fbsalesList.addAll(newList);}

        notifyItemRangeChanged(0, fbsalesList.size());

    }
    public void removeSales(int position) {
        fbsalesList.remove(position);

        //  notifyItemRemoved(position);
    }
    public void clearData(){
        fbsalesList.clear();
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public  TextView id ,sfbidTxt,sfbiidTxt, sfbcidTxt ,sfbcountTxt, sfbprofitTxt, sfbdateTxt,sfbclnameTxt,sfbitnameTxt ;
        public  ImageView imgEditFBS,imgDeleteFBS;

        public  MyViewHolder(@NonNull View itemView)   {
            super(itemView);



            sfbidTxt = itemView.findViewById(R.id.row_sidfb);
            sfbiidTxt = itemView.findViewById(R.id.row_siidfb);
            sfbcountTxt = itemView.findViewById(R.id.row_scountfb);
            sfbprofitTxt = itemView.findViewById(R.id.row_sprofitfb);
            sfbcidTxt = itemView.findViewById(R.id.id);
            sfbdateTxt = itemView.findViewById(R.id.row_sdatefb);



          sfbitnameTxt= itemView.findViewById(R.id.row_siname);
          sfbclnameTxt= itemView.findViewById(R.id.row_scname);


             imgDeleteFBS = itemView.findViewById(R.id.imgDeleteFBS);

        }
    }


}
