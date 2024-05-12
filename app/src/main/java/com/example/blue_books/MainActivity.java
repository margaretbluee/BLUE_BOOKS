package com.example.blue_books;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.blue_books.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static BookstoreDatabase myAppDatabaseBookstore;
    public static BookstoreDatabase db;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static FirebaseFirestore fbdb;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();

        //local db
        db = Room.databaseBuilder(getApplicationContext(),BookstoreDatabase.class,"bookstoreDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        //remote db
        fbdb=FirebaseFirestore.getInstance();



        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            //  onOptionsItemSelected()
            }
        });

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_home);

            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_book, R.id.nav_bookstore, R.id.nav_branch, R.id.nav_FBItems, R.id.nav_FBClients, R.id.nav_FBSales)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            drawer.closeDrawer(GravityCompat.START);
            int id = item.getItemId();
            if (id == R.id.nav_book) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_book);

            } else if (id == R.id.nav_bookstore) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_bookstore);

            } else if (id == R.id.nav_branch) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_branch);

            } else if (id == R.id.nav_FBItems) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_FBItems);

            } else if (id == R.id.nav_FBSales) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_FBSales);

            } else if (id == R.id.nav_FBClients) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_FBClients);

            } else if (id == R.id.nav_home) {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_home);

            }

            return false;
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Handle the settings item click, navigate to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

                super.onBackPressed();
                finishAffinity(); // This closes all activities in the task.

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}