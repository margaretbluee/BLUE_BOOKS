package com.example.blue_books;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Branch_RecyclerViewAdapter extends RecyclerView.Adapter<Branch_RecyclerViewAdapter.MyViewHolder>{
    //  private ArrayList<Items> items;
    private List<Branch> branchList;
    private Context context;
     private AdapterListenerBranch adapterListenerBranch;


    public Branch_RecyclerViewAdapter(Context context, AdapterListenerBranch listener, List<Branch> branchList) {

        this.context = context;
        this.adapterListenerBranch= listener;
        this.branchList=branchList;


    }



    @NonNull
    @Override
    public MyViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewBranch= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_branch,parent,false);
        return new MyViewHolder(viewBranch);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder  holder,  int position) {
 Branch branch =  branchList.get(position);


        holder.brIdTxt.setText(String.valueOf(branch.getBrid()));
        holder.brNameTxt.setText(branch.getBrname());
        holder.brAfmTxt.setText(String.valueOf(branch.getAfm()));
         holder.brTelTxt.setText(String.valueOf(branch.getTel()));
        holder.brAdrTxt.setText(branch.getAdr());

       // holder.brbookstorenameTxt.setText(branch.getBrbooksotrename());

       holder.brbookstoreidTxt.setText(String.valueOf(branch.getBrbooksotreid()));
        holder.brbookstorenameTxt.setText(String.valueOf(MainActivity.db.myDaoBookstore().getBookstoreName(String.valueOf(branch.getBrbooksotreid()))));

        holder.imgDeleteBr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListenerBranch.OnDeleteBranch(branch , holder.getAdapterPosition());
                //  removeItems( position );
            }
        });

        holder.imgEditBr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListenerBranch.OnUpdateBranch(branch, holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return branchList.size();
    }


    public void addItems(Branch branch) {
        branchList.add(branch);

        notifyItemRangeChanged(0, branchList.size());

    }
    public void showItems(List<Branch> newList) {
        if(newList!=branchList)    {   branchList.addAll(newList);}

        notifyItemRangeChanged(0, branchList.size());

    }
    public void removeItems(int position) {
        branchList.remove(position);

        //  notifyItemRemoved(position);
    }
    public void clearData(){
        branchList.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public  TextView brIdTxt,brNameTxt, brAfmTxt, brTelTxt, brAdrTxt, brbookstorenameTxt ,brbookstoreidTxt;
         public ImageView imgEditBr,imgDeleteBr;

        public  MyViewHolder(@NonNull View branchView) {
            super(branchView);

            brbookstoreidTxt= branchView.findViewById(R.id.row_brbookstoreid);

            brbookstorenameTxt= branchView.findViewById(R.id.row_brbookstorename);


            brIdTxt = branchView.findViewById(R.id.row_brid);
            brNameTxt = branchView.findViewById(R.id.row_brname);
            brAfmTxt = branchView.findViewById(R.id.row_brafm);
            brTelTxt = branchView.findViewById(R.id.row_brtel);
            brAdrTxt = branchView.findViewById(R.id.row_bradr);

           imgEditBr = branchView.findViewById(R.id.row_branch_edit);
             imgDeleteBr = branchView.findViewById(R.id.row_branch_delete);

        }
    }


}
