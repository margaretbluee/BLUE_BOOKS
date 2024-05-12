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

public class Dashb_BookFragment extends Fragment implements AdapterListenerBook{

    public Dashb_BookFragment(){ }
    RecyclerView recviewbook;
    BookstoreDatabase db;
    Book_RecyclerViewAdapter bookRecyclerViewAdapter;
    AdapterListenerBook adapterListenerBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashb_book, container, false);

        recviewbook =  view.findViewById(R.id.dashBookListId);



        getActivity().setTitle("ΒΙΒΛΙΑ");


        List<Book> book = new ArrayList<>();


        bookRecyclerViewAdapter = new Book_RecyclerViewAdapter(getContext(),   this, book);
        recviewbook.setAdapter(bookRecyclerViewAdapter);
        recviewbook.setLayoutManager(new LinearLayoutManager(getContext()));
        bookRecyclerViewAdapter.showItems(book);

        return  view;
    }


    @Override
    public void OnDeleteBook(Book book, int pos) {
        List<Book> bookList;
        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        try{
            bookList = db.myDaoBookstore().getBooks();
            db.myDaoBookstore().DeleteBook(book);
            bookRecyclerViewAdapter.removeItems(pos);
            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }}



    @Override
    public void onResume(){
        super.onResume();
        bookRecyclerViewAdapter.clearData();
        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        List<Book> books  = db.myDaoBookstore().getBooks();
        // items_recyclerViewAdapter.clearData();
        bookRecyclerViewAdapter = new Book_RecyclerViewAdapter(getContext(), this, books);
        recviewbook.setAdapter(bookRecyclerViewAdapter);
        recviewbook.setLayoutManager(new LinearLayoutManager(getContext()));
        bookRecyclerViewAdapter.showItems(books);

    }

    @Override
    public void OnUpdateBook(Book book , int pos) {


        Intent intent=new Intent(getContext(),edit_book.class);
        intent.putExtra("model",book);
        startActivity(intent);}}