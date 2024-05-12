package com.example.blue_books;


import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Bookstore.class, Branch.class, Book.class}, version = 5, exportSchema = false)
public abstract class BookstoreDatabase extends RoomDatabase {


    public abstract MyDaoBookstore myDaoBookstore();

}


