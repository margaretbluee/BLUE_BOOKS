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

public class edit_book extends AppCompatActivity {
    EditText NameEtb,   SigrafeasEtnb ,   PriceEtb  ;
    MaterialButton save;
  SeekBar seekBarPrice;
     Book book;
    Spinner spinnerCategory;
    TextView  IsbnEtb, CatEtb, bbrid, bbsid, bcount;
    ImageView close;

      BookstoreDatabase db;
      MyDaoBookstore myDaoBookstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        db = Room.databaseBuilder(getApplicationContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        setTitle("ΕΠΕΞΕΡΓΑΣΙΑ ΒΙΒΛΙΟΥ");


        myDaoBookstore=db.myDaoBookstore();

        IsbnEtb=findViewById(R.id.bookIsbn);
        NameEtb=findViewById(R.id.bookName);
        PriceEtb=findViewById(R.id.bookPrice);
        CatEtb=findViewById(R.id.bookCat);
        SigrafeasEtnb=findViewById(R.id.bookSigrafeas);
        bbsid=findViewById(R.id.bbsid);
        bbrid=findViewById(R.id.bbrid);
        bcount=findViewById(R.id.bcount);



        seekBarPrice=findViewById(R.id.seekBarPRICE);
        spinnerCategory =  findViewById(R.id.spinnerCateg);

        save =findViewById(R.id.btnEditBook);
          close = findViewById(R.id.closeAlertBook);


        book=(Book) getIntent().getSerializableExtra("model");

        IsbnEtb.setText(String.valueOf(book.getBisbn()));
        NameEtb.setText(book.getBname() );
        PriceEtb.setText(String.valueOf( book.getBprice()));
        CatEtb.setText( book.getBcategory()) ;
        SigrafeasEtnb.setText(    book.getBekdotis() );
        bcount.setText(String.valueOf(book.getBcount()));
        bbsid.setText(String.valueOf(book.getBbookstoreid()));
        bbrid.setText(String.valueOf(book.getBbranchid()));


/////////////////////SEEK BAR GIA UPDATE TIMIS BOOK
        seekBarPrice.setProgress(book.getBprice());

        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                PriceEtb.setText(String.valueOf( progress) );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //////////////////////////SPINNER ME KATIGORIES

        List<String> categories = new ArrayList<>();
        categories.add(0, " ΚΑΤΗΓΟΡΙΑ ");
        categories.add("ΞΕΝΟΓΛΩΣΣΑ");
        categories.add("ΛΟΓΟΤΕΧΝΙΑ");
        categories.add("ΕΠΙΣΤΗΜΕΣ");
        categories.add("ΠΑΙΔΙΚΑ");
        categories.add("ΕΓΚΥΚΛΟΠΑΙΔΕΙΕΣ");
        categories.add("ΤΕΧΝΟΛΟΓΙΑ");
        categories.add("ΤΑΞΙΔΙΑ");
        categories.add("ΚΟΜΙΚΣ");
        categories.add("ΑΥΤΟΒΕΛΤΙΩΣΗ");
        categories.add("ΛΕΞΙΚΑ");
        categories.add("ΜΑΓΕΙΡΙΚΗ");
        categories.add("ΨΥΧΟΛΟΓΙΑ");
        categories.add("ΠΟΙΗΣΗ");
        categories.add("ΥΓΕΙΑ");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals(" ΚΑΤΗΓΟΡΙΑ ")){}
                else{String item= parent.getItemAtPosition(position).toString();

                    CatEtb.setText(String.valueOf(item));
                    spinnerCategory.setSelection(0);
                    //     CatEtn.setText(categories.);
                    //  test = position+1;
                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {  @Override
        public void onClick(View v) {

            finish();
        }});
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   Book bookmodel = new Book();

                    bookmodel.setBisbn(Integer.parseInt(IsbnEtb.getText().toString()));
                    bookmodel.setBname(NameEtb.getText().toString());
                    bookmodel.setBprice(Integer.parseInt(PriceEtb.getText().toString()));
                    bookmodel.setBcategory(CatEtb.getText().toString() );
                    bookmodel.setBekdotis(SigrafeasEtnb.getText().toString());
                    bookmodel.setBcount(Integer.parseInt(bcount.getText().toString()));

                    bookmodel.setBbranchid(Integer.parseInt(bbrid.getText().toString()));

                    bookmodel.setBbookstoreid(Integer.parseInt(bbsid.getText().toString()));

                    MainActivity.db.myDaoBookstore().UpdateBook(bookmodel);

                    Toast.makeText(getApplicationContext(), "RECORD UPDATED ", Toast.LENGTH_SHORT).show();}

                catch (Exception e) {
                    e.printStackTrace(); // Print the stack trace of the exception
                    Toast.makeText(getApplicationContext(), "Failed to update: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }}







