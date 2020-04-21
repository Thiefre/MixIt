package com.example.mixit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_grocery_list, R.id.navigation_discover, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        listView = findViewById(R.id.listView);

        myList = new ArrayList<String>();
        myList.add("Eggs");
        myList.add("Chicken");
        myList.add("Beef");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
           public boolean onQueryTextSubmit(String query)
           {
               if(myList.contains(query))
               {
                   adapter.getFilter().filter(query);
               }
               else
               {
                   Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
               }
               return false;
           }
           public boolean onQueryTextChange(String newText)
           {
               if(newText == null || newText.equals(""))
               {
                   listView.setVisibility(View.GONE);
               }
               else
               {
                   listView.setVisibility(View.VISIBLE);
               }
               adapter.getFilter().filter(newText);
               return false;
           }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
