package com.example.blue_books;

import static com.example.blue_books.MainActivity.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;


public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView sales, books, bookstores, branches, fbclients, fbbooks;
    ImageView img_fbclients, img_book, img_fbbooks, img_fbsales, img_bookstore, img_branch;
    FirebaseFirestore fbdb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //   sharedPreferenceConfig = new sharedPreferenceConfig(getActivity().getApplicationContext());
        img_branch = view.findViewById(R.id.img_branch);
        img_bookstore = view.findViewById(R.id.img_bookstore);
        img_book = view.findViewById(R.id.img_book);
        img_fbbooks = view.findViewById(R.id.img_fbbooks);
        img_fbsales = view.findViewById(R.id.img_fbsales);
        img_fbclients = view.findViewById(R.id.img_fbclients);

        db = Room.databaseBuilder(getContext(),BookstoreDatabase.class,"bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
          fbdb = FirebaseFirestore.getInstance();

        books = view.findViewById(R.id.num_books);
        books.setText(String.valueOf(db.myDaoBookstore().getBooksCount()));

        bookstores = view.findViewById(R.id.num_bookstores);
        bookstores.setText(String.valueOf(db.myDaoBookstore().getBookstoreCount()));

        branches = view.findViewById(R.id.num_branches);
        branches.setText(String.valueOf(db.myDaoBookstore().getBranchesCount()));


        sales = view.findViewById(R.id.num_fbsales);
        CollectionReference collectionReference = fbdb.collection("FB_Sales");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the count of documents in the result set
                int count = queryDocumentSnapshots.size();
sales.setText(String.valueOf(count));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             }
        });


        fbclients = view.findViewById(R.id.num_fbclients);
        CollectionReference collectionReference1 = fbdb.collection("FB_Clients");
        collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the count of documents in the result set
                int count1 = queryDocumentSnapshots.size();

                fbclients.setText(String.valueOf(count1));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });


        fbbooks = view.findViewById(R.id.num_fbbooks);
        CollectionReference collectionReference2 = fbdb.collection("FB_Items");
        collectionReference2.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Get the count of documents in the result set
                int count2 = queryDocumentSnapshots.size();
                fbbooks.setText(String.valueOf(count2));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });


        // Set click listeners for each ImageView
        img_fbclients.setOnClickListener(this);
        img_fbsales.setOnClickListener(this);
        img_fbbooks.setOnClickListener(this);
        img_branch.setOnClickListener(this);
        img_book.setOnClickListener(this);
        img_bookstore.setOnClickListener(this);
        setTitle("ΑΡΧΙΚΗ ΣΕΛΙΔΑ");

        return view;
    }
    private void setTitle(String title) {
        // Check if the fragment is attached to an activity
        if (getActivity() != null && getActivity().getActionBar() != null) {
            // Set the title in the activity's action bar
            getActivity().setTitle(title);
        }
    }
    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        int actionId = -1;
        if (v.getId() == R.id.img_fbclients) {
            actionId = R.id.action_client;
        } else if (v.getId() == R.id.img_fbsales) {
            actionId = R.id.actionFbsales;
        } else if (v.getId() == R.id.img_fbbooks) {
            actionId = R.id.actionFbitems;
        } else if (v.getId() == R.id.img_branch) {
            actionId = R.id.actionBranch;
        } else if (v.getId() == R.id.img_book) {
            actionId = R.id.actionBook; // Assuming the same destination fragment as img_fbbooks
        } else if (v.getId() == R.id.img_bookstore) {
            actionId = R.id.actionBookstore;
        }

        if (actionId != -1) {
            navController.navigate(actionId);
        }
    }

}