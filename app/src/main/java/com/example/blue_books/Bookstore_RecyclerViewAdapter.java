package com.example.blue_books;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


class Bookstore_RecyclerViewAdapter extends RecyclerView.Adapter<Bookstore_RecyclerViewAdapter.MyViewHolder>{
    List<Bookstore> bookstoresList;

    private Context context;
    private AdapterListenerBookstore bookstoreadapterListener;
    public Bookstore_RecyclerViewAdapter(Context context, AdapterListenerBookstore listener,    List<Bookstore> bookstores) {
        this.context = context;
        this.bookstoreadapterListener = listener;
        this.bookstoresList=bookstores;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_bookstore,parent,false);
        return new MyViewHolder(viewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {
 Bookstore bookstore = bookstoresList.get(position);


        int bs_id=  Integer.parseInt( (String.valueOf(bookstore.getBsid())));



        holder.bookstore_idTxt.setText(String.valueOf(bookstore.getBsid()));
        holder.bookstore_nameTxt.setText(bookstore.getBsname());



       holder.imgDeleteBS.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bookstoreadapterListener.OnDeleteBookstore(bookstore, holder.getAdapterPosition());
               //  removeItems( position );
           }
       });

       holder.imgEditBS.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bookstoreadapterListener.OnUpdateBookstore(bookstore , holder.getAdapterPosition());
               notifyItemChanged(holder.getAdapterPosition());
           }
       });
    }


    @Override
    public int getItemCount() {
        return bookstoresList.size();
    }


    public void addItems(Bookstore fb_clients) {
        bookstoresList.add(fb_clients);

        notifyItemRangeChanged(0, bookstoresList.size());

    }
    public void showItems(List<Bookstore> newList) {
        if(newList!=bookstoresList)    {   bookstoresList.addAll(newList);}

        notifyItemRangeChanged(0, bookstoresList.size());

    }
    public void removeItems(int position) {
        bookstoresList.remove(position);

        //  notifyItemRemoved(position);
    }
    public void clearData(){
        bookstoresList.clear();
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public  TextView bookstore_idTxt,bookstore_nameTxt  ;
        public ImageView imgEditBS,imgDeleteBS;
       // RatingBar stars;

        public  MyViewHolder(@NonNull View bookstoreView) {
            super(bookstoreView);

            bookstore_idTxt = bookstoreView.findViewById(R.id.row_bisbn);
            bookstore_nameTxt = bookstoreView.findViewById(R.id.row_bookstore);


             imgEditBS = bookstoreView.findViewById(R.id.row_bookstore_edit);
            imgDeleteBS = bookstoreView.findViewById(R.id.row_bookstore_delete);

        }
    }


}
