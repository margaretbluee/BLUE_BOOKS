package com.example.blue_books;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class edit_bookstore extends AppCompatActivity {
    EditText NameEtbs   ;
    MaterialButton savebs;
      Bookstore bookstore;
    TextView  Idbs ;
    ImageView closebs;

    BookstoreDatabase db;
    MyDaoBookstore myDaoBookstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bookstore);
        db = Room.databaseBuilder(getApplicationContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        setTitle("ΕΠΕΞΕΡΓΑΣΙΑ ΒΙΒΛΙΟΠΩΛΕΙΟΥ");

        myDaoBookstore=db.myDaoBookstore();

        Idbs=findViewById(R.id.bookstoreId);
        NameEtbs=findViewById(R.id.bookstoreName);

        savebs=findViewById(R.id.btnEditBookstore);
        closebs = findViewById(R.id.closeAlertBookstore);


        bookstore=(Bookstore) getIntent().getSerializableExtra("model");
        Idbs.setText(String.valueOf(bookstore.getBsid()));
        NameEtbs.setText(bookstore.getBsname());


        closebs.setOnClickListener(new View.OnClickListener() {  @Override
        public void onClick(View v) {

            finish();
        }});
        savebs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bookstore bookstoremodel = new Bookstore();

                    bookstoremodel.setBsid(Integer.parseInt(Idbs.getText().toString()));
                    bookstoremodel.setBsname(NameEtbs.getText().toString());


                    MainActivity.db.myDaoBookstore().UpdateBookstore(bookstoremodel);

                    Toast.makeText(getApplicationContext(), "RECORD UPDATED ", Toast.LENGTH_SHORT).show();}

                catch (Exception e ){
                    Toast.makeText(getApplicationContext(), "FAIL to access DB", Toast.LENGTH_SHORT).show();


                }  finish();
            }
        });
    }}







