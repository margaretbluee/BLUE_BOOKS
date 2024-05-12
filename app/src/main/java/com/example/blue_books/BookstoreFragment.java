package com.example.blue_books;

import static com.example.blue_books.MainActivity.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;

import java.util.List;


public class BookstoreFragment extends Fragment implements AdapterListenerBookstore {
    RecyclerView recview_bs;

    EditText et_id, et_name;

    MaterialButton btn_insert_bookstore;


    private MyDaoBookstore myDaoBookstore;
    private Bookstore_RecyclerViewAdapter bookstore_recyclerViewAdapter;
    List<Bookstore> bookstores;
     BookstoreDatabase bookstoreDatabase;

    public BookstoreFragment() {

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_bookstore, container, false);

        recview_bs = view.findViewById(R.id.BookstoreListId);
        bookstoreDatabase = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

       myDaoBookstore = bookstoreDatabase.myDaoBookstore();

      //  Bookstore ianos = new Bookstore(1,"ΙΑΝΟΣ" );

     //   myDaoBookstore.InsertBookstore(ianos);

        bookstores = bookstoreDatabase.myDaoBookstore().getBookstores();

        Boolean check = myDaoBookstore.bs_exists(1);
        Bookstore gwnia  = new Bookstore(1,"Γωνιά του Βιβλίου" );
        if (!check){
         //   bookstoreDatabase.myDaoBookstore().DeleteBookstore(gwnia);
          myDaoBookstore.InsertBookstore(gwnia);
        }

         check = myDaoBookstore.bs_exists(2);
        Bookstore evr = new Bookstore(2,"Ευριπίδης");
        if (!check){
            //      bookstoreDatabase.myDaoBookstore().DeleteBookstore(evr);
         myDaoBookstore.InsertBookstore(evr);
        }

            check = myDaoBookstore.bs_exists(3);
            Bookstore el = new Bookstore(3,"Ελευθερουδάκη");
            if (!check){
                //  bookstoreDatabase.myDaoBookstore().DeleteBookstore(el);
               myDaoBookstore.InsertBookstore(el);
            }


          check =  myDaoBookstore.bs_exists(4);
                Bookstore pol = new Bookstore(4, "Παπασωτηρίου");
                if (!check){
                   // bookstoreDatabase.myDaoBookstore().DeleteBookstore(pol);
                     myDaoBookstore.InsertBookstore(pol);
                }


        et_id = view.findViewById(R.id.bs_et_id);
        et_name = view.findViewById(R.id.bs_et_name);
        btn_insert_bookstore = view.findViewById(R.id.bn_insertItems);
        recview_bs = view.findViewById(R.id.BookstoreListId);

        bookstore_recyclerViewAdapter = new Bookstore_RecyclerViewAdapter(getContext(), this, bookstores);
        recview_bs.setAdapter(bookstore_recyclerViewAdapter);
        recview_bs.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_insert_bookstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookstoreDatabase db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

                myDaoBookstore = db.myDaoBookstore();

                int var_bookstoreid = 0;
                try {
                    var_bookstoreid = Integer.parseInt(et_id.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }

                //  String var_bookstorename= et_name.getText().toString();
                Boolean check = myDaoBookstore.bs_exists(var_bookstoreid);

                if (!check) {
                    String var_bookstorename = et_name.getText().toString();


                    try {
                        Bookstore bookstore = new Bookstore();

                        bookstore.setBsid(var_bookstoreid);
                        bookstore.setBsname(var_bookstorename);

                        myDaoBookstore.InsertBookstore(bookstore);

                        bookstore_recyclerViewAdapter.addItems(bookstore);

                        bookstore_recyclerViewAdapter.notifyItemRangeChanged(0, bookstore.getBsid());
                        Toast.makeText(getActivity(), "RECORD ADDED IN DB", Toast.LENGTH_SHORT).show();


                        et_id.setText("");
                        et_name.setText("");

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "FAIL to access DB", Toast.LENGTH_SHORT).show();
                    }
                } else {
         /*       iname.setText("");
          icount.setText("");
          iprice.setText("");
          icategory.setText("");*/
                    Toast.makeText(getActivity(), "ALREADY EXISTS", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // binding = null;
    }

    @Override
    public void OnUpdateBookstore(Bookstore bookstore, int pos) {
        Intent intent=new Intent(getContext(),edit_bookstore.class);
        intent.putExtra("model",bookstore);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        bookstore_recyclerViewAdapter.clearData();
        BookstoreDatabase db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        bookstores = db.myDaoBookstore().getBookstores();
        // items_recyclerViewAdapter.clearData();
        bookstore_recyclerViewAdapter = new Bookstore_RecyclerViewAdapter(getContext(), this, bookstores
        );
        recview_bs.setAdapter(bookstore_recyclerViewAdapter);
        recview_bs.setLayoutManager(new LinearLayoutManager(getContext()));
        bookstore_recyclerViewAdapter.showItems(bookstores);

    }

    @Override
    public void OnDeleteBookstore(Bookstore bookstore, int pos) {
        List<Bookstore> bookstoreList;
         db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        try {
            bookstoreList = db.myDaoBookstore().getBookstores();
            db.myDaoBookstore().DeleteBookstore(bookstore);
            bookstore_recyclerViewAdapter.removeItems(pos);

            recview_bs.removeViewAt(pos);

            bookstore_recyclerViewAdapter.notifyItemRemoved(pos);
            bookstore_recyclerViewAdapter.notifyItemRangeChanged(pos, bookstoreList.size());

        } catch (Exception e) {
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

}