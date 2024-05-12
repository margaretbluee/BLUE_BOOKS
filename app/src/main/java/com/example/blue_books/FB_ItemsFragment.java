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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FB_ItemsFragment extends Fragment implements AdapterListenerFBItems  {
    private MyDaoBookstore myDaoBookstore;
    private BookstoreDatabase myAppBookstoreDatabase;

    RecyclerView recviewfbi;
    RatingBar star;

    EditText  fbiname,  fbiprice, fbirating;
TextView fbicount,fbiid, fbicat;
    MaterialButton btnAdd;
    private FBItems_RecyclerViewAdapter fbitems_recyclerViewAdapter;
    Spinner spinner_products;

    Spinner fbspinnercd;
      List<FB_Items>  items;
    private String CHANNEL_ID;

    public FB_ItemsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fb_items, container, false);

        recviewfbi = view.findViewById(R.id.ItemsListIdfb);

        List<FB_Items> fb_items = new ArrayList<>();

        fbitems_recyclerViewAdapter = new FBItems_RecyclerViewAdapter(getContext(), this, fb_items);
        recviewfbi.setAdapter(fbitems_recyclerViewAdapter);
        recviewfbi.setLayoutManager(new LinearLayoutManager(getContext()));
        fbitems_recyclerViewAdapter.showItems(fb_items);


        fbiid =  view.findViewById(R.id.fbiid);
        fbiname = view.findViewById(R.id.fbiname);

        fbicount = view.findViewById(R.id.fbicount);
        fbiprice = view.findViewById(R.id.fbiprice);
        fbirating = view.findViewById(R.id.fbirating);
        fbicat = view.findViewById(R.id.fbicat);
fbicat.setText("-");
fbiid.setText("-");
        recviewfbi.invalidate();


////////////////////SPINNER gia vivlia//////////////////////////////////

    spinner_products = view.findViewById(R.id.fbspinnerNames);

        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        List<String>  names = new ArrayList<>();
         names= db.myDaoBookstore().getBookNames();
         names.add(0,"ΒΙΒΛΙΑ");

        ArrayAdapter<String> arrayAdapter_s = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
        arrayAdapter_s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_products.setAdapter(arrayAdapter_s);


        spinner_products.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("ΠΡΟΪΟΝΤΑ")){}
                else{String item4= parent.getItemAtPosition(position).toString();

                    // CatEtn.setText(item);
                     fbiname.setText(item4);

                    String category = db.myDaoBookstore().getBooksCategory_withName(item4);
                    fbicat.setText(String.valueOf(category));

                    int count= db.myDaoBookstore().getBooksCount_withName(item4);
                    fbicount.setText(String.valueOf(count));

                    int iid = db.myDaoBookstore().getBooksIsbn_withName(item4);
                    fbiid.setText(String.valueOf(iid));

                    spinner_products.setSelection(position);
                //    String categ =
                    //  test = position+1;
                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String nothing = "-";
                fbicat.setText(String.valueOf(nothing));

            }
        });

        ///////////////////     //////////////////
        star=(RatingBar)view.findViewById(R.id.ratingBarfbi) ;

        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                         fbirating.setText(String.valueOf(rating) );

                }}  );

/////////////////////////////////////////////////////////////////////////////////////////////////

     /*   int count=25;
        fbicount.setText(String.valueOf(count));

ImageView plus, minus;
        plus= view.findViewById(R.id.imgplusfbi);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_salecount = 0;

                try {

                    Var_salecount = Integer.parseInt(fbicount.getText().toString());
                    Var_salecount ++;
                    fbicount.setText(String.valueOf(Var_salecount));
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }

            }
        });

        minus= view.findViewById(R.id.imgMinusfbi);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Var_salecount = 0;
                try {
                    Var_salecount = Integer.parseInt(fbicount.getText().toString());
                    Var_salecount--;
                    fbicount.setText(String.valueOf(Var_salecount));
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
            }}); */




        //   getroomdata();

        btnAdd = view.findViewById(R.id.bn_fbinserti);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             int Var_fbitemsid = 0;
                    try {
                        Var_fbitemsid = Integer.parseInt(fbiid.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    String Var_fbitemsname = fbiname.getText().toString();
                    int Var_fbitemscount = 0;
                    try {
                        Var_fbitemscount = Integer.parseInt(fbicount.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    int Var_fbitemsprice = 0;
                    try {
                        Var_fbitemsprice = Integer.parseInt(fbiprice.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    float Var_fbitemsrat = 0;
                try {
                    Var_fbitemsrat = Float.parseFloat(String.valueOf(fbirating.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                    String Var_fbitemscategory = fbicat.getText().toString();


                    try {
                        FB_Items fb_items = new FB_Items();
                        //          (Var_itemsid,Var_itemsname,Var_itemscount, Var_itemsprice,Var_itemsfpa,Var_itemscategory);

                        fb_items.setFb_item_id(Var_fbitemsid);
                         fb_items.setFb_item_name(Var_fbitemsname);
                        fb_items.setFb_item_count(db.myDaoBookstore().getBooksCount_withName(Var_fbitemsname));
                        fb_items.setFb_item_rating(Var_fbitemsrat);
                        fb_items.setFb_item_price(Var_fbitemsprice);
                        fb_items.setFb_item_cat(Var_fbitemscategory);

                        fbitems_recyclerViewAdapter.addfbItems(fb_items);

                        MainActivity.fbdb.collection("FB_Items")
                                .document(""+Var_fbitemsid).
                                set(fb_items).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(getActivity(), "RECORD ADDED IN FIREBASE DB", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(),"add operation failed.",Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } catch (Exception e) {
                        String message = e.getMessage();
                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                    }

                  //      items_recyclerViewAdapter.addItems(items);
                     //   items_recyclerViewAdapter.notifyItemRangeChanged(0, items.getCount());



                fbiid.setText("");
                        fbiname.setText("");
                        fbicount.setText("");
                        fbiprice.setText("");
                        fbicat.setText("");
                     }




        });

        return view;
    }





    @Override
    public void OnDeleteFBI(FB_Items fb_items, int id, int pos) {
        List<FB_Items> fbitemsList;
          try{

              CollectionReference collectionReference= MainActivity.fbdb.collection("FB_Items");
               collectionReference.document(String.valueOf(id)).delete()
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {
                              Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_SHORT).show();

                          }


                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_SHORT).show();
                          }
                      });
              recviewfbi.removeViewAt(pos);
              fbitems_recyclerViewAdapter.notifyItemRemoved(pos);

              fbitems_recyclerViewAdapter.removeItems(pos);



            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }}



    @Override
    public void onResume(){
        super.onResume();
    fbitems_recyclerViewAdapter.clearData();
        List<FB_Items> fb_items = new ArrayList<>();
        CollectionReference collectionReference =     MainActivity
                .fbdb.collection("FB_Items");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    FB_Items fb_items1 = documentSnapshot.toObject(FB_Items.class);
                    Integer  id = fb_items1.getFb_item_id();
                    String  name = fb_items1.getFb_item_name();
                    float rating = fb_items1.getFb_item_rating();
                    Integer price = fb_items1.getFb_item_price();
                    Integer count=fb_items1.getFb_item_count();
                    String  cat = fb_items1.getFb_item_cat();

                    fbitems_recyclerViewAdapter.addfbItems(fb_items1);
                }       }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_SHORT).show();
            }
        });

   //    fbitems_recyclerViewAdapter.clearData();
        fbitems_recyclerViewAdapter = new FBItems_RecyclerViewAdapter(getContext(), this, fb_items);
        recviewfbi.setAdapter(fbitems_recyclerViewAdapter);
        recviewfbi.setLayoutManager(new LinearLayoutManager(getContext()));
        fbitems_recyclerViewAdapter.showItems(fb_items);

    }

    @Override
    public void OnUpdateFBI(FB_Items fb_items,int pos){



     Intent intent=new Intent(getContext(),edit_fbitems.class);
       intent.putExtra("model",fb_items);
        startActivity(intent);
        }}
