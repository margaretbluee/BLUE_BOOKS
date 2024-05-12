package com.example.blue_books;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

import org.checkerframework.checker.nullness.qual.NonNull;


public class edit_fbclient extends AppCompatActivity {
TextView IdEtnfbc;
    EditText   NameEtnfbc,  CityEtnfbc ,RatEtnfbc   ;
    MaterialButton buttondialogfbc;
    Spinner spinnercc;
RatingBar stars;
int cid;///
    private FB_Clients fb_clients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fbclients);
        // db= Room.databaseBuilder(getApplicationContext(),EshopDatabase.class,"eshopBD").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        //   myDaoEshop=db.myDaoEshop();

        setTitle("ΕΠΕΞΕΡΓΑΣΙΑ ΠΕΛΑΤΗ");
        IdEtnfbc=findViewById(R.id.clientIdc);
        NameEtnfbc=findViewById(R.id.clientsNamedfb);
         CityEtnfbc=findViewById(R.id.clientsCityfb);
        RatEtnfbc=findViewById(R.id.clientsRatfb);
         buttondialogfbc=findViewById(R.id.btnEditdfbc);








         ImageView close_itemsfbc = findViewById(R.id.closeAlertClient);

        fb_clients=(FB_Clients)getIntent().getSerializableExtra("model");

        IdEtnfbc.setText(String.valueOf(fb_clients.getFb_client_id()));
        NameEtnfbc.setText(String.valueOf(fb_clients.getFb_client_name()));
       CityEtnfbc.setText( String.valueOf(fb_clients.getFb_client_city()));
        RatEtnfbc.setText(  String.valueOf(fb_clients.getFb_client_rating()));

///////////////////////////////////////////////////////////////////////////////////////////////




        stars=(RatingBar) findViewById(R.id.ratingBar3) ;
stars.setRating(fb_clients.getFb_client_rating());
        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                RatEtnfbc.setText(String.valueOf(rating) );

            }}  );

        NameEtnfbc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (NameEtnfbc.getText().toString().trim().matches("-?\\d+(.\\d+)?")){
                    NameEtnfbc.setError("Tα ονόματα δεν πρέπει να περιλαμβάνουν αριθμούς!");

                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (NameEtnfbc.getText().toString().trim().equalsIgnoreCase("") ){
                    NameEtnfbc.setError("Το πεδίο δε μπορεί να μείνει κενο!");
                }
            }
        });

        cid= Integer.parseInt(IdEtnfbc.getText().toString());
///////////////////////////////////////////////////////////////////////////////////////////////


        close_itemsfbc.setOnClickListener(new View.OnClickListener() {  @Override
        public void onClick(View v) {

            finish();
        }});

///////////////////////////////////////////////////////////////////////////////////////////////


        buttondialogfbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FB_Clients fb_clients= new FB_Clients();


                    fb_clients.setFb_client_id(Integer.parseInt(IdEtnfbc.getText().toString()));
                    fb_clients.setFb_client_name(NameEtnfbc.getText().toString());
                     fb_clients.setFb_client_rating(Float.parseFloat(RatEtnfbc.getText().toString()));
                    fb_clients.setFb_client_city(CityEtnfbc.getText().toString());
                     //fbdb  MainActivity.myAppDatabaseEshop.myDaoEshop().UpdateItems(itemsmodel);
                    //  items_recyclerViewAdapter.addItems(items);
                    //  items_recyclerViewAdapter.notifyItemRangeChanged(0, items.getCount());

                    MainActivity.fbdb.collection("FB_Clients").document(String.valueOf(cid)).set(fb_clients).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(edit_fbclient.this, "ΕΠΙΤΥΧΗΣ ΤΡΟΠΟΠΟΙΗΣΗ ..", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(edit_fbclient.this, "ΑΠΟΤΥΧΙΑ..", Toast.LENGTH_SHORT).show();
                        }
                    });

            }

        });
    }}






