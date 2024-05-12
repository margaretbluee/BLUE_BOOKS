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
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class Dashb_BookstoreFragment extends Fragment implements AdapterListenerBookstore{

    public Dashb_BookstoreFragment(){ }
    RecyclerView recviewbookstore;
    BookstoreDatabase db;
    Bookstore_RecyclerViewAdapter bookstoreRecyclerViewAdapter;
    AdapterListenerBookstore adapterListenerBookstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashb_boookstore, container, false);

        recviewbookstore =  view.findViewById(R.id.dashBookstoreListId);



        getActivity().setTitle("BIBΛΙΟΠΩΛΕΙΑ");


        List<Bookstore> bookstore = new ArrayList<>();


        bookstoreRecyclerViewAdapter = new Bookstore_RecyclerViewAdapter(getContext(),   this, bookstore);
        recviewbookstore.setAdapter(bookstoreRecyclerViewAdapter);
        recviewbookstore.setLayoutManager(new LinearLayoutManager(getContext()));
        bookstoreRecyclerViewAdapter.showItems(bookstore);

        return  view;
    }


    @Override
    public void OnDeleteBookstore(Bookstore bookstore, int pos) {
        List<Bookstore> bookstoreList;
        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        try{
            bookstoreList = db.myDaoBookstore().getBookstores();
            db.myDaoBookstore().DeleteBookstore(bookstore);
            bookstoreRecyclerViewAdapter.removeItems(pos);
            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
        }}



    @Override
    public void onResume(){
        super.onResume();
        bookstoreRecyclerViewAdapter.clearData();
        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        List<Bookstore> bookstores  = db.myDaoBookstore().getBookstores();
        // items_recyclerViewAdapter.clearData();
        bookstoreRecyclerViewAdapter = new Bookstore_RecyclerViewAdapter(getContext(), this, bookstores);
        recviewbookstore.setAdapter(bookstoreRecyclerViewAdapter);
        recviewbookstore.setLayoutManager(new LinearLayoutManager(getContext()));
        bookstoreRecyclerViewAdapter.showItems(bookstores);

    }

    @Override
    public void OnUpdateBookstore(Bookstore bookstore, int pos) {


        Intent intent=new Intent(getContext(),edit_bookstore.class);
        intent.putExtra("model",bookstore);
        startActivity(intent);}}