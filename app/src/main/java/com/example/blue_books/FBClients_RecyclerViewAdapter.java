package com.example.blue_books;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


class FBClients_RecyclerViewAdapter extends RecyclerView.Adapter<FBClients_RecyclerViewAdapter.MyViewHolder>{
    private List<FB_Clients> fbclientsList;
    private Context context;
    private AdapterListenerFBClients fbadapterListenerc;
    public FBClients_RecyclerViewAdapter(Context context, AdapterListenerFBClients listener, List<FB_Clients> fbclientsList) {
        this.context = context;
        this.fbadapterListenerc = listener;
        this.fbclientsList=fbclientsList;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_fbclients,parent,false);
        return new MyViewHolder(viewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {
FB_Clients fb_clients = fbclientsList.get(position);

        int cid=  Integer.parseInt( (String.valueOf(fb_clients.getFb_client_id())));

        holder.fbidcTxt.setText(String.valueOf(fb_clients.getFb_client_id()));
        holder.fbnamecTxt.setText(fb_clients.getFb_client_name());
         holder.fbcitycTxt.setText(String.valueOf(fb_clients.getFb_client_city()));
        holder.fbratcTxt.setText((String.valueOf(fb_clients.getFb_client_rating())));

        holder.stars.setRating(fb_clients.getFb_client_rating());

        holder.imgDeleteFBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbadapterListenerc.OnDeleteFBC(fb_clients , cid, holder.getAdapterPosition());
                //  removeItems( position );
            }
        });

        holder.imgEditFBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbadapterListenerc.OnUpdateFBC(fb_clients, holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return fbclientsList.size();
    }


    public void addfbClients(FB_Clients fb_clients) {
        fbclientsList.add(fb_clients);

        notifyItemRangeChanged(0, fbclientsList.size());

    }
    public void showItems(List<FB_Clients> newList) {
        if(newList!=fbclientsList)    {   fbclientsList.addAll(newList);}

        notifyItemRangeChanged(0, fbclientsList.size());

    }
    public void removeItems(int position) {
        fbclientsList.remove(position);

        //  notifyItemRemoved(position);
    }
    public void clearData(){
        fbclientsList.clear();
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public  TextView fbidcTxt,fbnamecTxt , fbcitycTxt, fbratcTxt;
        public  ImageView imgEditFBC,imgDeleteFBC;
        RatingBar stars;

        public  MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fbidcTxt = itemView.findViewById(R.id.row_clidfb);
            fbnamecTxt = itemView.findViewById(R.id.row_clnamefb);
             fbcitycTxt = itemView.findViewById(R.id.row_clcityfb);
            fbratcTxt = itemView.findViewById(R.id.row_clratingfb);
            stars = itemView.findViewById(R.id.ratingBarrowfbc);

            imgEditFBC = itemView.findViewById(R.id.imgEditFBCl);
            imgDeleteFBC = itemView.findViewById(R.id.imgDeleteFBCl);

        }
    }


}
