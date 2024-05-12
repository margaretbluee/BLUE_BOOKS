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


class FBItems_RecyclerViewAdapter extends RecyclerView.Adapter<FBItems_RecyclerViewAdapter.MyViewHolder>{
      List<FB_Items> fbitemsList;
     Context context;
     AdapterListenerFBItems fbadapterListener;
    public FBItems_RecyclerViewAdapter(Context context, AdapterListenerFBItems listener, List<FB_Items> fbitemsList) {
         this.context = context;
        this.fbadapterListener = listener;
        this.fbitemsList=fbitemsList;

     }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_fbitems,parent,false);
        return new MyViewHolder(viewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {

        FB_Items fbitems = fbitemsList.get(position);

        int iid=  Integer.parseInt( (String.valueOf(fbitems.getFb_item_id())));
int count = MainActivity.db.myDaoBookstore().getBooksCount_withName(fbitems.getFb_item_name());

        holder.fbidTxt.setText(String.valueOf(fbitems.getFb_item_id()));
        holder.fbnameTxt.setText(fbitems.getFb_item_name());
        holder.fbcountTxt.setText(String.valueOf(fbitems.getFb_item_count()));
        holder.fbpriceTxt.setText(String.valueOf(fbitems.getFb_item_price()));
        holder.fbratTxt.setText(String.valueOf(fbitems.getFb_item_rating()));
        holder.fbcategoryTxt.setText(fbitems.getFb_item_cat() );

        holder.stars.setRating(fbitems.getFb_item_rating());

        holder.imgDeleteFBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbadapterListener.OnDeleteFBI(fbitems , iid, holder.getAdapterPosition());
                //  removeItems( position );
            }
        });

        holder.imgEditFBI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbadapterListener.OnUpdateFBI(fbitems, holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return fbitemsList.size();
    }


    public void addfbItems(FB_Items fb_items) {
        fbitemsList.add(fb_items);

        notifyItemRangeChanged(0, fbitemsList.size());

    }
    public void showItems(List<FB_Items> newList) {
        if(newList!=fbitemsList)    {   fbitemsList.addAll(newList);}

        notifyItemRangeChanged(0, fbitemsList.size());

    }
    public void removeItems(int position) {
        fbitemsList.remove(position);

        //  notifyItemRemoved(position);
    }
    public void clearData(){
        fbitemsList.clear();
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public  TextView fbidTxt,fbnameTxt, fbcountTxt, fbpriceTxt, fbratTxt, fbcategoryTxt;
        public  ImageView imgEditFBI,imgDeleteFBI;
        public RatingBar stars;

        public  MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fbidTxt = itemView.findViewById(R.id.row_sidfb);
            fbnameTxt = itemView.findViewById(R.id.row_inamefb);
            fbcountTxt = itemView.findViewById(R.id.row_scountfb);
            fbpriceTxt = itemView.findViewById(R.id.titlos);
            fbratTxt = itemView.findViewById(R.id.row_afmfb);
            fbcategoryTxt = itemView.findViewById(R.id.row_icategfb);

            imgEditFBI = itemView.findViewById(R.id.imgEditFBI);
            imgDeleteFBI = itemView.findViewById(R.id.imgDeleteFBI);


            stars= itemView.findViewById(R.id.ratingBarrowfbc);
        }
    }


}
