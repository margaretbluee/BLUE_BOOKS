package com.example.blue_books;

 import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class Dashb_BranchFragment extends Fragment implements AdapterListenerBranch{

    public Dashb_BranchFragment(){ }
    RecyclerView recviewbranch;
    BookstoreDatabase db;
    ImageView filteraz,filterza;
Branch_RecyclerViewAdapter branchRecyclerViewAdapter;
AdapterListenerBranch adapterListenerBranch;

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashb_branch, container, false);

    recviewbranch =  view.findViewById(R.id.dashBranchListId);



        getActivity().setTitle("ΥΠΟΚΑΤΑΣΤΗΜΑΤΑ");


        List<Branch> branch = new ArrayList<>();

      filteraz = view.findViewById(R.id.FILTERaz);
    filterza = view.findViewById(R.id.FILTERza);

    filteraz.setVisibility(View.VISIBLE);
    branchRecyclerViewAdapter = new Branch_RecyclerViewAdapter(getContext(),   this, branch);
    recviewbranch.setAdapter(branchRecyclerViewAdapter);
    recviewbranch.setLayoutManager(new LinearLayoutManager(getContext()));
    branchRecyclerViewAdapter.showItems(branch);

    filteraz.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


    List<Branch> asc0;
    asc0 = db.myDaoBookstore().getBranchesSortByAscLastName();
    branchRecyclerViewAdapter = new Branch_RecyclerViewAdapter(getContext(), adapterListenerBranch , asc0);

    recviewbranch.setAdapter(branchRecyclerViewAdapter);
    recviewbranch.setLayoutManager(new LinearLayoutManager(getContext()));
            filterza.setVisibility(View.VISIBLE);
            filteraz.setVisibility(View.INVISIBLE);

        }


    });

    filterza.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            List<Branch> asc0;
            asc0 = db.myDaoBookstore().getBranchesSortByDescLastName();
            branchRecyclerViewAdapter = new Branch_RecyclerViewAdapter(getContext(), adapterListenerBranch , asc0);

            recviewbranch.setAdapter(branchRecyclerViewAdapter);
            recviewbranch.setLayoutManager(new LinearLayoutManager(getContext()));
            filterza.setVisibility(View.INVISIBLE);
            filteraz.setVisibility(View.VISIBLE);

        }


    });
    return  view;
    }


    @Override
    public void OnDeleteBranch(Branch branch, int pos) {
        List<Branch> branchList;
      db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
  try{
      branchList = db.myDaoBookstore().getBranches();
      db.myDaoBookstore().DeleteBranch(branch);
      branchRecyclerViewAdapter.removeItems(pos);
      Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();
  }catch(Exception e){
      Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
  }}



    @Override
    public void onResume(){
        super.onResume();
        branchRecyclerViewAdapter.clearData();
        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        List<Branch> branch  = db.myDaoBookstore().getBranches();
        // items_recyclerViewAdapter.clearData();
        branchRecyclerViewAdapter = new Branch_RecyclerViewAdapter(getContext(), this, branch);
        recviewbranch.setAdapter(branchRecyclerViewAdapter);
        recviewbranch.setLayoutManager(new LinearLayoutManager(getContext()));
        branchRecyclerViewAdapter.showItems(branch);

    }

    @Override
    public void OnUpdateBranch(Branch branch, int pos) {


        Intent intent=new Intent(getContext(),edit_branch.class);
        intent.putExtra("model",branch);
        startActivity(intent);}}