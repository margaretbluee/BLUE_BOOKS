package com.example.blue_books;


import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.blue_books.MainActivity.db;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FB_SalesFragment extends Fragment implements AdapterListenerFBSales, DatePickerDialog.OnDateSetListener {

    EditText fbsid, fbscid, fbsiid, fbsdate, fbscount;

    ImageView calendar;
    private static final int NOTIFICATION_ID = 1001;
    private static final int REQUEST_CODE_PERMISSION = 1002;


    MyDaoBookstore myDaoBookstore;
    TextView fbsiname, fbscname, fbsiid2, fbscid2, pricetv1, fbsprof;
    TextView teliko_apothema;
    TextView arxiko_apothema;
    TextView kostos_agoras, timi_polisis;
    ImageView plus, minus;
    Button ins;
    private FBSales_RecyclerViewAdapter fbsales_recyclerViewAdapter;
    List<FB_Sales> sales;
    RecyclerView recviewfbs;

    Spinner fbspinnerItems;
    Spinner fbspinnerClients;
    private NotificationCompat.Builder builder;

    public FB_SalesFragment() {
        // Required empty public constructor

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fb_sales, container, false);

        recviewfbs = view.findViewById(R.id.SalesListIdfb);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My notification";
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("My notification", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FB_Sales fbsales = new FB_Sales();

        List<FB_Sales> fb_sales = new ArrayList<>();


        fbsales_recyclerViewAdapter = new FBSales_RecyclerViewAdapter(getContext(), this, fb_sales);
        recviewfbs.setAdapter(fbsales_recyclerViewAdapter);
        recviewfbs.setLayoutManager(new LinearLayoutManager(getContext()));
        fbsales_recyclerViewAdapter.showSales(fb_sales);


        fbsid = view.findViewById(R.id.fbsid);

        fbsdate = view.findViewById(R.id.fbsdate);
        fbscount = view.findViewById(R.id.fbscount);
        fbsprof = view.findViewById(R.id.fbsprof);

        fbsiid2 = view.findViewById(R.id.fbsiid2);
        fbscid2 = view.findViewById(R.id.fbscid2);


        fbsiname = view.findViewById(R.id.fbsiname);
        fbscname = view.findViewById(R.id.fbscname);
        fbspinnerItems = view.findViewById(R.id.spinnerItems);

        teliko_apothema = view.findViewById(R.id.teliko_apothema);
        arxiko_apothema = view.findViewById(R.id.arxiko_apothema);
        timi_polisis = view.findViewById(R.id.timi_polisis);
        kostos_agoras = view.findViewById(R.id.kostos_agoras);

        recviewfbs.invalidate();


        List<Integer> fbitems_id = new ArrayList<>();
        List<Integer> fbitems_price = new ArrayList<>();

        List<String> fbitems_names = new ArrayList<>();

        CollectionReference collectionReference1 = MainActivity.fbdb.collection("FB_Items");
        collectionReference1.
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String fb_item_name;
                        int fb_item_id;
                        int fb_item_price;

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            FB_Items fb_items = documentSnapshot.toObject(FB_Items.class);
                            fb_item_name = fb_items.getFb_item_name();
                            fb_item_id = fb_items.getFb_item_id();
                            fb_item_price = fb_items.getFb_item_price();
                            fbitems_price.add(Integer.parseInt(String.valueOf(fb_item_price)));

                            fbitems_names.add(String.valueOf(fb_item_name));
                            fbitems_id.add(Integer.parseInt(String.valueOf(fb_item_id)));
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "query operation failed.", Toast.LENGTH_LONG).show();
                    }
                });

        fbitems_names.add(0, "ΒΙΒΛΙΟ");
        fbitems_id.add(0, 0);
        fbitems_price.add(0, 0);


        ArrayAdapter<String> arrayAdapter_fbi = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, fbitems_names);
        arrayAdapter_fbi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fbspinnerItems.setAdapter(arrayAdapter_fbi);


        fbspinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("ΒΙΒΛΙΟ")) {

                } else {
                    String item1 = parent.getItemAtPosition(position).toString();
                    fbsiname.setText(item1);
                    try {
                        String idtest = String.valueOf(fbitems_id.get(position));
                        fbsiid2.setText(String.valueOf(idtest));
                        String pricetest = String.valueOf(fbitems_price.get(position));

                        timi_polisis.setText(pricetest);

                        int arxiko = db.myDaoBookstore().getBooksCount_withName(item1);
                        arxiko_apothema.setText(String.valueOf(arxiko));

                        int count = Integer.parseInt(fbscount.getText().toString());
                        int teliko = arxiko - count;
                        teliko_apothema.setText(String.valueOf(teliko));

                        int agora = db.myDaoBookstore().getBookPrice_withName(item1);
                        kostos_agoras.setText(String.valueOf(agora));


                        int price = Integer.parseInt(pricetest);
                        int kostos = price * count;
                        fbsprof.setText(String.valueOf(kostos));
check_stock();
                        fbspinnerItems.setSelection(0);
                        NotificationManagerCompat manager = NotificationManagerCompat.from(getContext());

                    } catch (RuntimeException e) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////////////////////////////////////////////////////////////////////////
        fbspinnerClients = view.findViewById(R.id.fbspinnerClients);

        List<Integer> fbclients_id = new ArrayList<>();

        List<String> fbclients_names = new ArrayList<>();


        CollectionReference collectionReference2 = MainActivity.fbdb.collection("FB_Clients");
        collectionReference2.
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String fb_client_name;
                        int fb_client_id;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            FB_Clients fb_clients = documentSnapshot.toObject(FB_Clients.class);
                            fb_client_name = fb_clients.getFb_client_name();
                            fb_client_id = fb_clients.getFb_client_id();
                            fbclients_names.add(String.valueOf(fb_client_name));
                            fbclients_id.add(Integer.parseInt(String.valueOf(fb_client_id)));
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "query operation failed.", Toast.LENGTH_LONG).show();
                    }
                });


        fbclients_names.add(0, "ΠΕΛΑΤΗΣ");
        fbclients_id.add(0, 0);


        ArrayAdapter<String> arrayAdapter_fbc = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, fbclients_names);
        arrayAdapter_fbi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fbspinnerClients.setAdapter(arrayAdapter_fbc);


        fbspinnerClients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("ΠΕΛΑΤΗΣ")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    fbspinnerClients.setSelection(0);

                    fbscname.setText(item);
                    try {
                        String idtestc = String.valueOf(fbclients_id.get(position));
                        fbscid2.setText(idtestc);
                    } catch (RuntimeException e) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        calendar = view.findViewById(R.id.imgCalendarS);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setTargetFragment(FB_SalesFragment.this, 0);
                dialogFragment.show(getFragmentManager(), "datePicker");
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////
        int count = 10;
        fbscount.setText(String.valueOf(count));

        plus = view.findViewById(R.id.imgplus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // Parse sale count
                    check_stock();

                    int VarSaleCount = Integer.parseInt(fbscount.getText().toString());
                    VarSaleCount++;
                    fbscount.setText(String.valueOf(VarSaleCount));

                    // Parse price and count
                    int price = Integer.parseInt(timi_polisis.getText().toString());
                    int count = Integer.parseInt(fbscount.getText().toString());

                    // Update total cost
                    int totalCost = price * count;
                    fbsprof.setText(String.valueOf(totalCost));

                    // Update remaining stock
                    int initialStock = Integer.parseInt(arxiko_apothema.getText().toString());
                    int remainingStock = initialStock - VarSaleCount;
                    teliko_apothema.setText(String.valueOf(remainingStock));
                     builder = null;

                    // Check if remaining stock is less than or equal to 0
                    if (remainingStock < 0) {
                        // Notification Channel Creation (Do this only once, perhaps in onCreate())
                        builder = new NotificationCompat.Builder(getContext(), "My notification")
                                .setSmallIcon(R.drawable.baseline_warning_amber_24)
                                .setContentTitle("ΠΡΟΣΟΧΗ")
                                .setContentText("Το απόθεμα του προϊόντος εξαντλείται...")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("ΔΕ ΜΠΟΡΕΙΤΕ ΝΑ ΠΟΥΛΗΣΕΤΕ ΤΟΣΑ ΠΡΟΙΟΝΤΑ!"))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);
                    }

                    NotificationManagerCompat manager = NotificationManagerCompat.from(requireContext());

                    if (builder != null) {
                        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            // Handle permission request
                        }

                        manager.notify(1, builder.build());
                    }

                } catch (NumberFormatException ex) {
                    // Handle parsing errors
                    ex.printStackTrace(); // Or log the error
                    // Inform the user about the invalid input
                    Toast.makeText(getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });



        minus = view.findViewById(R.id.imgMinus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int Var_salecount = 0;
                try {
                    check_stock();

                    Var_salecount = Integer.parseInt(fbscount.getText().toString());
                    Var_salecount--;
                    fbscount.setText(String.valueOf(Var_salecount));

                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int price = Integer.parseInt(timi_polisis.getText().toString());
                int count = Integer.parseInt(fbscount.getText().toString());
                int kostos = price * count;
                fbsprof.setText(String.valueOf(kostos));

                int arx = Integer.parseInt(arxiko_apothema.getText().toString());
                int tel = arx - Var_salecount;
                teliko_apothema.setText(String.valueOf(tel));





            }
        });



////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ins = view.findViewById(R.id.insert_fbSales);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();


                myDaoBookstore = db.myDaoBookstore();

                int Var_saleid = 0;
                try {


                    Var_saleid = Integer.parseInt(fbsid.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }


                int Var_sitemid = 0;

                Var_sitemid = Integer.parseInt(fbsiid2.getText().toString());

                DocumentReference Var_itemid = MainActivity.fbdb.document("/FB_Items/" + Var_sitemid);

                int Var_sclientid = 0;

                Var_sclientid = Integer.parseInt(fbscid2.getText().toString());


                DocumentReference Var_clientid = MainActivity.fbdb.document("/FB_Clients/" + Var_sclientid);


                int Var_salecount = 0;
                try {
                    Var_salecount = Integer.parseInt(fbscount.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                int Var_prof = 0;
                try {
                    int price = Integer.parseInt(timi_polisis.getText().toString());
                    int count = Integer.parseInt(fbscount.getText().toString());
                    int kostos = price * count;
                    fbsprof.setText(String.valueOf(kostos));
                    Var_prof = Integer.parseInt(fbsprof.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                String Var_date = fbsdate.getText().toString();


                try {
                    FB_Sales fb_sales = new FB_Sales();

                    fb_sales.setFb_sales_id(Var_saleid);

                    fb_sales.setFb_item_id(Var_itemid);
                    fb_sales.setFb_client_id(Var_clientid);
                    fb_sales.setFb_sales_date(Var_date);
                    fb_sales.setFb_sales_count(Var_salecount);
                    fb_sales.setFb_sales_profit(Var_prof);

                    //   fbsales_recyclerViewAdapter.addfbSales(fb_sales);


                    //   Toast.makeText(getActivity(), "ENHMEΡΩΘΗΚΕ ΕΠΙΤΥΧΩΣ Η ΠΟΣΟΣΗΤΑ ΠΡΟΙΟΝΤΩΝ ΑΠΟΘΗΚΗΣ.", Toast.LENGTH_SHORT).show();

                    // transactions_recyclerViewAdapter.notifyItemRangeChanged(0, transactions.getCount());
                    MainActivity.fbdb.collection("FB_Sales").add(fb_sales).addOnSuccessListener(new OnSuccessListener<DocumentReference >() {



                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getActivity(), "RECORD ADDED IN FBDB", Toast.LENGTH_SHORT).show();
                            fbsales_recyclerViewAdapter.addfbSales(fb_sales);
                            MainActivity.fbdb.collection("FB_Sales")
                                    .document(String.valueOf(Var_itemid))
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            CollectionReference collectionReference = MainActivity
                                                    .fbdb.collection("FB_Items");

                                            collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                        FB_Items fb_items = documentSnapshot.toObject(FB_Items.class);
                                                        String fb_item_id = String.valueOf(fb_items.getFb_item_id());
                                                        String fb_item_name = fb_items.getFb_item_name();//krataw onoma gia na to sigkrinw me to onoma tis topikis vasis
                                                        int fb_item_price = fb_items.getFb_item_price();
                                                        float fb_item_rating = fb_items.getFb_item_rating();
                                                        String fb_item_cat = fb_items.getFb_item_cat();

                                                        if (Var_itemid.getPath().endsWith(fb_item_id)) {
                                                            int posotita_sinallagis = Integer.parseInt(fbscount.getText().toString());
                                                            int items_previous_count = MainActivity.db.myDaoBookstore().getBooksCount(MainActivity.db.myDaoBookstore().getBooksIsbn_withName(fb_item_name));
                                                            int items_new_count = items_previous_count - posotita_sinallagis;
                                                            fb_items.setFb_item_count(items_new_count);
                                                            //   fb_items.setFb_item_id(fb_item_id);
                                                            fb_items.setFb_item_rating(fb_item_rating);
                                                            fb_items.setFb_item_price(fb_item_price);
                                                            fb_items.setFb_item_cat(fb_item_cat);

                                                           db.myDaoBookstore().UpdateBooksCountwithName(fb_item_name, items_new_count);
                                                            Toast.makeText(getActivity(), "ENHMEΡΩΘΗΚΕ ΕΠΙΤΥΧΩΣ Η ΠΟΣΟΣΗΤΑ ΠΡΟΙΟΝΤΩΝ ΑΠΟΘΗΚΗΣ.", Toast.LENGTH_SHORT).show();
                                                            MainActivity.fbdb.collection("FB_Items").document("" + fb_item_id).set(fb_items).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(getActivity(), "ENHMEΡΩΘΗΚΕ ΕΠΙΤΥΧΩΣ Η ΠΟΣΟΣΗΤΑ ΠΡΟΙΟΝΤΩΝ ΠΟΛΗΣΗΣ..", Toast.LENGTH_SHORT).show();

                                                                }


                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getActivity(), "query operation failed.", Toast.LENGTH_LONG).show();
                                                                }
                                                            });

                                                        }

                                                    }
                                                }

                                                ;

                                            });
                                        }
                                    });
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "FAIL to access DB", Toast.LENGTH_LONG).show();
                }

                /////////////ENIMERWSI TIS POSOTITAS TOU ITEM(ROOM) POU PROMIITHEFITKA


            }});



        return view;
}


    public void check_stock() {
        int teliko = Integer.parseInt(teliko_apothema.getText().toString());
        if(teliko==0 || teliko<0){
            ins.setVisibility(View.INVISIBLE);
        }else{
            ins.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void OnUpdateFBS(FB_Sales fb_sales, int pos) {

    }

    @Override
    public void OnDeleteFBS(FB_Sales fb_sales, int id,  int pos) {
        List<FB_Sales> fbsalesList;
        try{

            CollectionReference collectionReference= MainActivity.fbdb.collection("FB_Sales");
            collectionReference.document(String.valueOf(id)).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_LONG).show();



                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
                        }
                    });
            recviewfbs.removeViewAt(pos);
            fbsales_recyclerViewAdapter.notifyItemRemoved(pos);

            fbsales_recyclerViewAdapter.removeSales(pos);

            Toast.makeText(getActivity(), "DELETED FROM DB", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onResume(){
        super.onResume();
       fbsales_recyclerViewAdapter.clearData();
        List<FB_Sales> fb_sales = new ArrayList<>();
        CollectionReference collectionReference =     MainActivity
                .fbdb.collection("FB_Sales");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    FB_Sales fb_sales1 = documentSnapshot.toObject(FB_Sales.class);
                    Integer  id = fb_sales1.getFb_sales_id();
                    DocumentReference iid= fb_sales1.getFb_item_id();

                    DocumentReference cid= fb_sales1.getFb_client_id();

                    fbsales_recyclerViewAdapter.addfbSales(fb_sales1);
                }       }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
            }
        });

        //    fbitems_recyclerViewAdapter.clearData();
        fbsales_recyclerViewAdapter = new FBSales_RecyclerViewAdapter(getContext(), this, fb_sales);
        recviewfbs.setAdapter(fbsales_recyclerViewAdapter);
        recviewfbs.setLayoutManager(new LinearLayoutManager(getContext()));
        fbsales_recyclerViewAdapter.showSales(fb_sales);

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        StringBuilder sb = new StringBuilder().append(dayOfMonth).append("/").append(month);
        String formattedDate =sb.toString();
        fbsdate.setText(formattedDate);
    }

}

