package com.example.blue_books;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;
import com.google.protobuf.StringValue;

public class edit_branch extends AppCompatActivity {
    EditText NameEtnc,AfmEtnc ,TelEtnc ,AdrEtnc  ;
    MaterialButton buttondialogc;
     Branch branch;
    TextView  IdEtnc, bsname, bsid;
    private BookstoreDatabase db;
    private MyDaoBookstore myDaoBookstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch);
         db = Room.databaseBuilder(getApplicationContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        setTitle("ΕΠΕΞΕΡΓΑΣΙΑ ΥΠΟΚΑΤΑΣΤΗΜΑΤΟΣ");

        myDaoBookstore=db.myDaoBookstore();

        IdEtnc=findViewById(R.id.branchId);
        NameEtnc=findViewById(R.id.branchName);
        AfmEtnc=findViewById(R.id.branchAfm);
        TelEtnc=findViewById(R.id.branchTel);
        AdrEtnc=findViewById(R.id.branchAdr);

         bsid=findViewById(R.id.branch_bs_id_et);

         buttondialogc=findViewById(R.id.btnEditBranch);
ImageView close = findViewById(R.id.closeAlertBranch);


        branch=(Branch) getIntent().getSerializableExtra("model");

        IdEtnc.setText(String.valueOf(branch.getBrid()));
        NameEtnc.setText(String.valueOf(branch.getBrname()));
        AfmEtnc.setText(String.valueOf( branch.getAfm()));
        TelEtnc.setText( String.valueOf(branch.getTel()));
        AdrEtnc.setText(  String.valueOf(branch.getAdr()));
        bsid.setText(String.valueOf(branch.getBrbooksotreid()));


      close.setOnClickListener(new View.OnClickListener() {  @Override
      public void onClick(View v) {

          finish();
      }});
        buttondialogc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Branch branchmodel = new Branch();

                    branchmodel.setBrid(Integer.parseInt(IdEtnc.getText().toString()));
                    branchmodel.setBrname(NameEtnc.getText().toString());
                    branchmodel.setAfm(Integer.parseInt(AfmEtnc.getText().toString()));
                    branchmodel.setTel( TelEtnc.getText().toString() );
                    branchmodel.setAdr(AdrEtnc.getText().toString());
                    branchmodel.setBrbooksotreid(Integer.parseInt(bsid.getText().toString()));

                    //  branchmodel.setBrbooksotreid(MainActivity.db.myDaoBookstore().getBookstoreIdFromBranchId(String.valueOf(Integer.parseInt((String.valueOf(IdEtnc))) )));
                    MainActivity.db.myDaoBookstore().UpdateBranch(branchmodel);

                    Toast.makeText(getApplicationContext(), "RECORD UPDATED ", Toast.LENGTH_SHORT).show();}

                catch (Exception e ){
                    Toast.makeText(getApplicationContext(), "FAIL to access DB", Toast.LENGTH_SHORT).show();


                }  finish();
            }
        });
    }}







