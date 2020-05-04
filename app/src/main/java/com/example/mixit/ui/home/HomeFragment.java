package com.example.mixit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> myList;
    SearchView searchView;
    TextView searchItems;
    ArrayList<String> addedItems;
    Button searchButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        searchButton = root.findViewById(R.id.searchByIngredientList);

        listView = root.findViewById(R.id.searchListView);
        addedItems = new ArrayList<String>();
        myList = new ArrayList<String>();
        myList.add("Eggs");
        myList.add("Chicken");
        myList.add("Beef");

        adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, myList);
        searchItems = (TextView)root.findViewById(R.id.searchIngredients);
        searchItems.setText("Ingredients: ");

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v;
                int count = parent.getChildCount();
                v = parent.getChildAt(position);
                TextView tv = (TextView)v;
                if(!addedItems.contains(tv.getText())) {
                    searchItems.append(tv.getText() + ", ");
                    addedItems.add(tv.getText().toString());
                }
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
        });

        searchView = root.findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
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
                    Toast.makeText(getContext(), "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }
            public boolean onQueryTextChange(String newText)
            {
                if(newText == null || newText.equals(""))
                {
                    listView.setVisibility(View.GONE);
                    searchButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    listView.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                }
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        //Search button function: Search by addedItems, create new Fragment that displays the found recipes(will probably use DiscoverFragment)

        return root;
    }
}
