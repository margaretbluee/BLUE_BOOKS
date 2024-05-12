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
import android.widget.ImageView;
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

public class BookFragment extends Fragment implements AdapterListenerBook   {


    RecyclerView recviewb;
    EditText bisbn, bname,bprice,  bsigrafeas, bcount;
    TextView bbsid, bbsname, bbrid,bcat, bbrname;
    MaterialButton btn_insertBook;
ImageView chbs, chbr, chcat;
    Spinner spinnerBookstores;
    Spinner spinnerBranches, spinnerCategory;
    private BookstoreDatabase bookstoreDatabase;
    private MyDaoBookstore myDaoBookstore;
     Book_RecyclerViewAdapter book_recyclerViewAdapter;
    List<Book> books;
ImageView imgDel_b, imgUpd_b;

    public BookFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        recviewb  = view.findViewById(R.id.BookListId);
        BookstoreDatabase db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();


        books = db.myDaoBookstore().getBooks();


        bisbn =  view.findViewById(R.id.book_isbn);
        bname = view.findViewById(R.id.book_title);
        bcount=view.findViewById(R.id.b_count);
        bsigrafeas = view.findViewById(R.id.b_sigrafeas);
        bprice = view.findViewById(R.id.b_price);
        bcat = view.findViewById(R.id.b_cat);
        chcat=view.findViewById(R.id.checkcat);
        chbs=view.findViewById(R.id.checkbs);
        chbr=view.findViewById(R.id.checkbr);

        bbrid=view.findViewById(R.id.b_branchid);
        bbrname=view.findViewById(R.id.b_branchname);
        bbsid=view.findViewById(R.id.b_bookstoreid);
        bbsname=view.findViewById(R.id.b_bookstorename);
        recviewb=view.findViewById(R.id.BookListId);

        recviewb.invalidate();

//PROSOXI STO THIS !!!!!
        book_recyclerViewAdapter = new Book_RecyclerViewAdapter(getContext(),this,books);
        recviewb.setAdapter(book_recyclerViewAdapter);
        recviewb.setLayoutManager(new LinearLayoutManager(getContext()));

        //SPINNER παιρνει ονοματα των boookstores kai grafei se ena edittext to id tou bookstore me vasi tin epilogi sto spinner
        spinnerBookstores = view.findViewById(R.id.spinnerBookstore);
        spinnerBranches = view.findViewById(R.id.spinnerBranch);
        final int[] Var_b_bookstoreid = {0};

        List<String> bookstore_name = new ArrayList<>();
        bookstore_name= db.myDaoBookstore().getBookstoresNames();
        bookstore_name.add(0,"BIBΛΙΟΠΩΛΕΙΟ");
        ArrayAdapter<String> arrayAdapter_i = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookstore_name);
        arrayAdapter_i.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookstores.setAdapter(arrayAdapter_i);


        spinnerBookstores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("BIBΛΙΟΠΩΛΕΙΟ")) {
                    Var_b_bookstoreid[0] = 0;
                } else {
                    String item1 = parent.getItemAtPosition(position).toString();
                    bbsname.setText(item1);
                    chbs.setVisibility(View.VISIBLE);
                    int iid = db.myDaoBookstore().getBookstoreId(item1);
                    bbsid.setText((String.valueOf(iid)));
                    Var_b_bookstoreid[0] = iid;
                    chbr.setVisibility(View.GONE);
                    chcat.setVisibility(View.GONE);
                    bcat.setText("");
                    bbrname.setText("");
                    // Populate branch spinner
                    populateBranchSpinner(Var_b_bookstoreid[0]);
                    spinnerBookstores.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Var_b_bookstoreid[0] = 0; // Set to null if nothing is selected
                // Populate branch spinner based on the selected bookstore (null in this case)
                populateBranchSpinner(0);
            }
        });
////////////////////

//spinner me katigories vivliwn
        spinnerCategory = view.findViewById(R.id.spinnerCategory);

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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("ΚΑΤΗΓΟΡΙΑ")){}
                else{String item= parent.getItemAtPosition(position).toString();

                    bcat.setText(String.valueOf(item));
                    spinnerCategory.setSelection(0);
                    //     CatEtn.setText(categories.);
                    //  test = position+1;
                    chcat.setVisibility(View.VISIBLE);

                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        btn_insertBook = view.findViewById(R.id.btn_insertBook);

        btn_insertBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookstoreDatabase db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                myDaoBookstore = db.myDaoBookstore();
                int Var_bisbn= 0;
                try {
                    Var_bisbn = Integer.parseInt(bisbn.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse " + ex);
                }
                Boolean check = myDaoBookstore.book_exists(Var_bisbn);

                if (!check) {
                    //foreign keys/refs
                    int Var_b_bookstoreid = 0;
                    try {
                        Var_b_bookstoreid = Integer.parseInt(bbsid.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    int Var_b_branchid = 0;

                    try {
                        Var_b_branchid = Integer.parseInt(bbrid.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    String Var_bname = bname.getText().toString();
                    int Var_bprice = 0;
                    try {
                        Var_bprice = Integer.parseInt(bprice.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }
                    int Var_bcount = 0;
                    try {
                        Var_bcount = Integer.parseInt(bcount.getText().toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Could not parse " + ex);
                    }

                    String Var_bcat = bcat.getText().toString();
                    String Var_bsigrafeas = bsigrafeas.getText().toString();




                    try {
                        Book book = new Book();

                       book.setBbookstoreid(Var_b_bookstoreid);
                        book.setBbranchid(Var_b_branchid);
                        book.setBisbn(Var_bisbn);
                        book.setBcategory(Var_bcat);
                        book.setBekdotis(Var_bsigrafeas);
                        book.setBname(Var_bname);
                        book.setBprice(Var_bprice);
                        book.setBcount(Var_bcount);

                        db.myDaoBookstore().InsertBook(book);

                        book_recyclerViewAdapter.addItems(book);
                        //    companies_recyclerViewAdapter.notifyItemRangeChanged(0, companies.ge());
                        Toast.makeText(getActivity(), "RECORD ADDED IN DB", Toast.LENGTH_SHORT).show();


                        //getroomdata(getView());

                        bisbn.setText("");
                        bname.setText("");
                        bprice.setText("");
                        bbsid.setText("");
                        bbrid.setText("");
                        bcat.setText("");
                        bbrname.setText("");
                        bcount.setText("");
                        bsigrafeas.setText("");
                        bbsname.setText("");

                        chbs.setVisibility(View.INVISIBLE);
                        chbr.setVisibility(View.GONE);
                        chcat.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "RECORD ADDED IN DB", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "FAIL to access DB", Toast.LENGTH_SHORT).show();
                    }
                } else {
         /*       iname.setText("");
          icount.setText("");
          iprice.setText("");
          icategory.setText("");*/
                    Toast.makeText(getActivity(), "ALREADY EXISTS", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void populateBranchSpinner(int bookstoreId) {
        List<String> branch_name = new ArrayList<>();
        if (bookstoreId != 0) {
            branch_name = db.myDaoBookstore().getBranchesNamesThatBelongToBookstore(bookstoreId);
        }
        branch_name.add(0, "BRANCH");
        ArrayAdapter<String> arrayAdapter_br = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, branch_name);
        arrayAdapter_br.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBranches.setAdapter(arrayAdapter_br);

        spinnerBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("BRANCH")) {
                } else {
                    String item2 = parent.getItemAtPosition(position).toString();
                    bbrname.setText(item2);
                    String namebr = item2;
                    int idbr = db.myDaoBookstore().getBranchId(namebr);
                    bbrid.setText((String.valueOf(idbr)));
                    int Var_b_branchid = Integer.parseInt(bbrid.getText().toString());

                    chbr.setVisibility(View.VISIBLE);

                    //SPINNER WITH NULL OPTION
                    spinnerBranches.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    @Override
    public void OnUpdateBook(Book book, int pos) {
        Intent intent=new Intent(getContext(),edit_book.class);
        intent.putExtra("model",book);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        book_recyclerViewAdapter.clearData();
         db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        books = db.myDaoBookstore().getBooks();
        // items_recyclerViewAdapter.clearData();
        book_recyclerViewAdapter = new Book_RecyclerViewAdapter(getContext(), this, books
        );
        recviewb.setAdapter(book_recyclerViewAdapter);
        recviewb.setLayoutManager(new LinearLayoutManager(getContext()));
        book_recyclerViewAdapter.showItems(books);

    }

    @Override
    public void OnDeleteBook(Book book, int pos) {
        List<Book> booksList;
        db = Room.databaseBuilder(getContext(), BookstoreDatabase.class, "bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        try {
            booksList = db.myDaoBookstore().getBooks();
            MainActivity.db.myDaoBookstore().DeleteBook(book);
            book_recyclerViewAdapter.removeItems(pos);
           recviewb.removeViewAt(pos);

            book_recyclerViewAdapter.notifyItemRemoved(pos);
            book_recyclerViewAdapter.notifyItemRangeChanged(pos, booksList.size());

        } catch (Exception e) {
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}