package com.example.blue_books;

import static com.example.blue_books.MainActivity.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class BranchFragment extends Fragment implements AdapterListenerBranch    {


    RecyclerView recviewbr;
    EditText brid, brname,brafm, brtel, bradr, brbookstoreid;
TextView br_bookstoreid,br_bookstorename;
    MaterialButton btnAddBranch;

Spinner spinnerBookstores;
    private BookstoreDatabase bookstoreDatabase;
    private MyDaoBookstore myDaoBookstore;
    private Branch_RecyclerViewAdapter branch_recyclerViewAdapter;
    List<Branch> branches;


    public BranchFragment() {
    }

    @Override
     public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch, container, false);

        recviewbr = view.findViewById(R.id.branchListId);
         db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();


        branches = db.myDaoBookstore().getBranches();


        // Access DAO from the database instance
         myDaoBookstore = db.myDaoBookstore();

          Boolean check = db.myDaoBookstore().branch_exists(11);

        if (  !check){
            Branch gwniatr  = new Branch(11,"ΤΡΙΚΑΛΑ" ,1234,"6923456677"," Σφακτηρίας 67",1 );

             db.myDaoBookstore().InsertBranch(gwniatr);
        }
          check = db.myDaoBookstore().branch_exists(1);

        if (  !check){
            Branch gwniafl  = new Branch(1,"ΦΛΩΡΙΝΑ" ,1234,"6934556677","Ελευθερίας  37",1 );

            db.myDaoBookstore().InsertBranch(gwniafl);
        }
          check = db.myDaoBookstore().branch_exists(111);

        if (  !check){
            Branch gwniakar  = new Branch(111,"ΚΑΡΔΙΤΣΑ" ,1234,"69434256677","Αγίου Δημητρίου 17",1 );

            db.myDaoBookstore().InsertBranch(gwniakar);
        }

        check = myDaoBookstore.branch_exists(22);
        if (  !check){
              Branch evr  = new Branch(22,"ΚΑΒΑΛΑ" ,67812,"6933445677","Πλατείας 21",2);

             db.myDaoBookstore().InsertBranch(evr);
        }
        check = myDaoBookstore.branch_exists(222);
        if (  !check){
            Branch evr  = new Branch(222,"ΞΑΝΘΗ" ,67812,"6956445677","Αγίου Κωνσταντίνου 24",2);

            db.myDaoBookstore().InsertBranch(evr);
        }

        check = myDaoBookstore.branch_exists(2);
        if (  !check){
            Branch evr  = new Branch(2,"ΧΑΛΚΙΔΑ" ,67812,"6936445677","Ζωγράφου 24",2);

            db.myDaoBookstore().InsertBranch(evr);
        }
        check = myDaoBookstore.branch_exists(3);
        if ( !check){
            Branch el  = new Branch(3,"ΚΑΛΑΜΑΤΑ" ,6789,"6945441134","Ανδρονίκου 144",3);

            db.myDaoBookstore().InsertBranch(el);
        }

        check = myDaoBookstore.branch_exists(33);
        if ( !check){
            Branch el  = new Branch(33,"ΧΙΟΣ" ,6789,"6944991134","Παναγίας 424",3);

             db.myDaoBookstore().InsertBranch(el);
        }
        check = myDaoBookstore.branch_exists(333);
        if ( !check){
            Branch el  = new Branch(333,"ΑΘΗΝΑ" ,6789,"6945991134","Ευαγγελιστρίας 44",3);

            db.myDaoBookstore().InsertBranch(el);
        }
        check =  myDaoBookstore.branch_exists(4);
        if (  !check){
            Branch pol  = new Branch(4,"ΕΒΡΟΣ" ,71620589,"6991145934","Καρόλου 14",4);

            db.myDaoBookstore().InsertBranch(pol);
        }


        check =  myDaoBookstore.branch_exists(44);
        if (  !check){
            Branch pol  = new Branch(44,"ΚΡΗΤΗ" ,716920589,"6991145934","Κοραή 14",4);

            db.myDaoBookstore().InsertBranch(pol);
        }
        check =  myDaoBookstore.branch_exists(444);
        if (  !check){
            Branch pol  = new Branch(444,"ΡΟΔΟΣ" ,71620589,"6991145934","Δικαστηρίων 14",4);

            db.myDaoBookstore().InsertBranch(pol);
        }

       brid =  view.findViewById(R.id.br_id);
       brname = view.findViewById(R.id.br_name);

        brafm = view.findViewById(R.id.br_afm);
        brtel = view.findViewById(R.id.br_tel);
        bradr = view.findViewById(R.id.br_adr);
        br_bookstoreid= view.findViewById(R.id.b_bookstoreid);
        br_bookstorename= view.findViewById(R.id.b_branchname);
         recviewbr.invalidate();

//PROSOXI STO THIS !!!!!
        branch_recyclerViewAdapter = new Branch_RecyclerViewAdapter(getContext(), this, branches);
        recviewbr.setAdapter(branch_recyclerViewAdapter);
        recviewbr.setLayoutManager(new LinearLayoutManager(getContext()));

 //SPINNER παιρνει ονοματα των boookstores kai grafei se ena edittext to id tou bookstore
        spinnerBookstores = view.findViewById(R.id.spinnerBookstores);

        List<String> bookstore_name = new ArrayList<>();
        bookstore_name= db.myDaoBookstore().getBookstoresNames();
        bookstore_name.add(0,"BIBΛΙΟΠΩΛΕΙΟ");
        ArrayAdapter<String> arrayAdapter_i = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookstore_name);
        arrayAdapter_i.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookstores.setAdapter(arrayAdapter_i);


        spinnerBookstores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("BIBΛΙΟΠΩΛΕΙΟ")){}
                else{String item1= parent.getItemAtPosition(position).toString();
                    br_bookstorename.setText(item1);
                    String name= item1;
                    int iid= db.myDaoBookstore().getBookstoreId(name);
                    br_bookstoreid.setText((String.valueOf(iid)));
                    int  Var_br_bookstoreid = Integer.parseInt(br_bookstoreid.getText().toString());


                    //SPINNER WITH NULL OPTION
                    spinnerBookstores.setSelection(0);

                }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








        btnAddBranch = view.findViewById(R.id.btn_branch_insert);

        btnAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookstoreDatabase db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
myDaoBookstore = db.myDaoBookstore();
                 int Var_brid = 0;
                try {
                    Var_brid = Integer.parseInt(brid.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                Boolean check = myDaoBookstore.branch_exists(Var_brid);

                if (!check) {
                    int Var_br_bookstoreid = 0;
                    try {
                        Var_br_bookstoreid = Integer.parseInt(br_bookstoreid.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    String Var_brname = brname.getText().toString();
                    int Var_brafm = 0;
                    try {
                        Var_brafm = Integer.parseInt(brafm.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }


                    String Var_bradr = bradr.getText().toString();
                    String Var_brtel = brtel.getText().toString();


                    try {
                        Branch branch= new Branch();
                        //          (Var_itemsid,Var_itemsname,Var_itemscount, Var_itemsprice,Var_itemsfpa,Var_itemscategory);

                        branch.setBrid(Var_brid);
                        branch.setBrname(Var_brname);
                        branch.setAfm(Var_brafm);
                        branch.setTel(Var_brtel);
                        branch.setAdr(Var_bradr);
                        branch.setBrbooksotreid(Var_br_bookstoreid);

                        db.myDaoBookstore().InsertBranch(branch);

                        branch_recyclerViewAdapter.addItems(branch);
                        //    companies_recyclerViewAdapter.notifyItemRangeChanged(0, companies.ge());
                        Toast.makeText(getActivity(), "RECORD ADDED IN DB", Toast.LENGTH_SHORT).show();


                        //getroomdata(getView());

                        brid.setText("");
                        brname.setText("");
                        brafm.setText("");
                        brtel.setText("");
                        bradr.setText("");
                        Toast.makeText(getContext(), "RECORD ADDED IN DB", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "FAIL to access DB", Toast.LENGTH_SHORT).show();
                    }
                } else {
         /*       iname.setText("");
          icount.setText("");
          iprice.setText("");
          icategory.setText("");*/
                    Toast.makeText(getActivity(), "ALREADY EXISTS", Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void OnUpdateBranch(Branch branch, int pos) {
        Intent intent=new Intent(getContext(),edit_branch.class);
        intent.putExtra("model",branch);
        startActivity(intent);
    }
    @Override
    public void onResume(){
        super.onResume();
        branch_recyclerViewAdapter.clearData();
      db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        branches = db.myDaoBookstore().getBranches();
        // items_recyclerViewAdapter.clearData();
        branch_recyclerViewAdapter = new Branch_RecyclerViewAdapter(getContext(), this, branches);
        recviewbr.setAdapter(branch_recyclerViewAdapter);
        recviewbr.setLayoutManager(new LinearLayoutManager(getContext()));
        branch_recyclerViewAdapter.showItems(branches);

    }
    @Override
    public void OnDeleteBranch(Branch branch, int pos) {
List<Branch> branchList;
         db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
try{
    branchList= db.myDaoBookstore().getBranches();
    db.myDaoBookstore().DeleteBranch(branch);
   branch_recyclerViewAdapter.removeItems(pos);

    recviewbr.removeViewAt(pos);
    branch_recyclerViewAdapter.notifyItemRemoved(pos);

    branch_recyclerViewAdapter.notifyItemRangeChanged(pos, branchList.size());



}catch(Exception e){
    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
}
    }
}