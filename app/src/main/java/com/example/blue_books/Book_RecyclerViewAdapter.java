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


public class Book_RecyclerViewAdapter extends RecyclerView.Adapter<Book_RecyclerViewAdapter.MyViewHolder>{
    //  private ArrayList<Items> items;
    private List<Book> bookList;
    private Context context;
     private AdapterListenerBook adapterListenerBook;


    public Book_RecyclerViewAdapter(Context context, AdapterListenerBook listener, List<Book> bookList) {

        this.context = context;
        this.adapterListenerBook= listener;
        this.bookList=bookList;


    }



    @NonNull
    @Override
    public MyViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewBook= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_book,parent,false);
        return new MyViewHolder(viewBook);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder  holder,  int position) {
 Book book =  bookList.get(position);


        holder.bIsbnTxt.setText(String.valueOf(book.getBisbn()));
        holder.bNameTxt.setText(book.getBname());
        holder.bpriceTxt.setText(String.valueOf(book.getBprice()));
         holder.bSigrafeasTxt.setText(book.getBekdotis());
        holder.bcategoryTxt.setText(book.getBcategory());

     //bookstore references
       holder.bbsidTxt.setText(String.valueOf(book.getBbookstoreid()));
        holder.bbsnameTxt.setText(String.valueOf(MainActivity.db.myDaoBookstore().getBookstoreName(String.valueOf(book.getBbookstoreid()))));
        //branch references
        holder.bbridTxt.setText(String.valueOf(book.getBbranchid()));
        holder.bbrnameTxt.setText(String.valueOf(MainActivity.db.myDaoBookstore().getBranchName(String.valueOf(book.getBbranchid()))));

        holder.imgDeleteBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListenerBook.OnDeleteBook(book , holder.getAdapterPosition());
                //  removeItems( position );
            }
        });

        holder.imgEditBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListenerBook.OnUpdateBook(book, holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public void addItems(Book book) {
        bookList.add(book);

        notifyItemRangeChanged(0, bookList.size());

    }
    public void showItems(List<Book> newList) {
        if(newList!=bookList)    {   bookList.addAll(newList);}

        notifyItemRangeChanged(0, bookList.size());

    }
    public void removeItems(int position) {
        bookList.remove(position);

        //  notifyItemRemoved(position);
    }
    public void clearData(){
        bookList.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public  TextView bIsbnTxt,bNameTxt, bSigrafeasTxt, bbridTxt, bbsidTxt,bbrnameTxt, bbsnameTxt, bpriceTxt ,bcategoryTxt;
        public ImageView imgEditBR,imgDeleteBR;

        public  MyViewHolder(@NonNull View bookView) {
            super(bookView);
//BRANCH
            bbridTxt= bookView.findViewById(R.id.row_bbranchid);

            bbrnameTxt= bookView.findViewById(R.id.row_bbranchname);

        //BOOKSTORE REF
            bbsidTxt= bookView.findViewById(R.id.row_bbookstoreid);

            bbsnameTxt= bookView.findViewById(R.id.row_bbookstorename);

            bIsbnTxt = bookView.findViewById(R.id.row_bisbn);
            bNameTxt = bookView.findViewById(R.id.row_bname);
            bSigrafeasTxt = bookView.findViewById(R.id.row_bsigrafeas);
            bpriceTxt = bookView.findViewById(R.id.row_bprice);
            bcategoryTxt = bookView.findViewById(R.id.row_bcategory);

           imgEditBR = bookView.findViewById(R.id.row_book_edit);
            imgDeleteBR = bookView.findViewById(R.id.row_book_delete);

        }
    }


}
