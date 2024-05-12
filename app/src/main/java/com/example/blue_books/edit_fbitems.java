package com.example.blue_books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


public class edit_fbitems extends AppCompatActivity {

    EditText  PriceEtnfb ,RatEtnfb  ;
    TextView IdEtnfb,CatEtnfb, NameEtnfb,CountEtnfb;
    String name, cat;
    int iid, count, price;
    float rat;
    MaterialButton buttondialogfb;
    Spinner spinnerc;
    RatingBar stars;


    private FB_Items fb_items;

    String regexString = "^[0-9]*$" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fbitems);
        // db= Room.databaseBuilder(getApplicationContext(),EshopDatabase.class,"eshopBD").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        //   myDaoEshop=db.myDaoEshop();
        setTitle("ΕΠΕΞΕΡΓΑΣΙΑ ΒΙΒΛΙΟΥ");
        IdEtnfb=findViewById(R.id.trid);
        NameEtnfb=findViewById(R.id.itemsNamedfb);
        CountEtnfb=findViewById(R.id.trcount);
        PriceEtnfb=findViewById(R.id.trdisc);
        RatEtnfb=findViewById(R.id.itemsRatdfb);
        CatEtnfb=findViewById(R.id.itemsCatdfb);
        buttondialogfb=findViewById(R.id.btnEditdt);




        buttondialogfb =findViewById(R.id.btnEditdt);
        ImageView close_itemsfb = findViewById(R.id.closeAlertdt);

        fb_items=(FB_Items)getIntent().getSerializableExtra("model");

        IdEtnfb.setText(String.valueOf(fb_items.getFb_item_id()));
        NameEtnfb.setText(String.valueOf(fb_items.getFb_item_name()));
        CountEtnfb.setText(String.valueOf(fb_items.getFb_item_count()));
        PriceEtnfb.setText( String.valueOf(fb_items.getFb_item_price()));
        RatEtnfb.setText(  String.valueOf(fb_items.getFb_item_rating()));
        CatEtnfb.setText( String.valueOf(fb_items.getFb_item_cat()));


///////////////////////////////////////////////////////////////////////////////////////////////
        stars=(RatingBar) findViewById(R.id.ratingBar2) ;
        stars.setRating(fb_items.getFb_item_rating());

        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                RatEtnfb.setText(String.valueOf(rating) );

            }}  );
///////////////////////////////////////////////////////////////////////////////////////////////
        SeekBar bar = findViewById(R.id.seekBarPRICE);
        bar.setProgress(fb_items.getFb_item_price());
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                PriceEtnfb.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////
        spinnerc = findViewById(R.id.spinnerCategfb);

        List<String> categories = new ArrayList<>();

        categories.add(0, "ΚΑΤΗΓΟΡΙΑ");
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
        spinnerc.setAdapter(arrayAdapter);

        spinnerc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("ΚΑΤΗΓΟΡΙΑ")){}
                else{String item= parent.getItemAtPosition(position).toString();

                    CatEtnfb.setText(item);
                    spinnerc.setSelection(0);
                    //     CatEtn.setText(categories.);
                    //  test = position+1;
                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        iid= Integer.parseInt(IdEtnfb.getText().toString());


        NameEtnfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (NameEtnfb.getText().toString().trim().matches("-?\\d+(.\\d+)?")){
                    NameEtnfb.setError("Tα ονόματα δεν πρέπει να περιλαμβάνουν αριθμούς!");

                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (NameEtnfb.getText().toString().trim().equalsIgnoreCase("") ){
                    NameEtnfb.setError("Το πεδίο δε μπορεί να μείνει κενο!");
                }
            }
        });
        PriceEtnfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() >= 3){
                    PriceEtnfb.setError("Τιμή εκτός ορίων!");
                }
            }



            @Override
            public void afterTextChanged(Editable s) {
                if (PriceEtnfb.getText().toString().trim().equalsIgnoreCase("")){
                    PriceEtnfb.setError("Παρακαλώ συμπληρώστε την τιμή!");
                }

            }
        });

        CountEtnfb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() >= 3){
                    CountEtnfb.setError("Ποσότητα εκτός ορίων!");
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (CountEtnfb.getText().toString().trim().equalsIgnoreCase("")){
                    CountEtnfb.setError("Παρακαλώ συμπληρώστε την ποσότητα!");
                }


            }
        });



        close_itemsfb.setOnClickListener(new View.OnClickListener() {  @Override
        public void onClick(View v) {

            finish();
        }});

        buttondialogfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FB_Items fb_items= new FB_Items();
                fb_items.setFb_item_id(Integer.parseInt(IdEtnfb.getText().toString()));
                fb_items.setFb_item_name(NameEtnfb.getText().toString());
                fb_items.setFb_item_count(Integer.parseInt(CountEtnfb.getText().toString()));
                fb_items.setFb_item_rating(Float.parseFloat(RatEtnfb.getText().toString()));
                fb_items.setFb_item_price(Integer.parseInt(PriceEtnfb.getText().toString()));
                fb_items.setFb_item_cat(CatEtnfb.getText().toString());



                MainActivity.fbdb.collection("FB_Items").document(String.valueOf(iid)).set(fb_items).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(edit_fbitems.this, "ΕΠΙΤΥΧΗΣ ΤΡΟΠΟΠΟΙΗΣΗ ..", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(edit_fbitems.this, "ΑΠΟΤΥΧΙΑ..", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
    }}







