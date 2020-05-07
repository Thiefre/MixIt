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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.FileHelper;
import com.example.mixit.R;
import com.example.mixit.ui.recipes.Recipe;
import com.example.mixit.ui.recipes.RecipeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView searchingListView;
    ListView selectedListView;
    ArrayAdapter<String> searchingAdapter;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> databaseList;
    SearchView searchView;
    ArrayList<String> addedItems;
    Button searchButton;
    Button clearButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        searchButton = root.findViewById(R.id.searchByIngredientListBtn);
        clearButton = root.findViewById(R.id.clear_btn);

        searchingListView = root.findViewById(R.id.searchListView);
        selectedListView = root.findViewById(R.id.list_ingredients);

        addedItems = FileHelper.readData(getActivity(), "listinfo2.dat");
        databaseList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.my_foods)));

        searchingAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, databaseList);
        listAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, addedItems);

        searchingListView.setAdapter(searchingAdapter);
        selectedListView.setAdapter(listAdapter);
        searchingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v;
                v = parent.getChildAt(position);
                TextView tv = (TextView)v;

                String itemEntered = tv.getText().toString();
                if(!addedItems.contains(itemEntered)) {
                    listAdapter.add(itemEntered);
                    FileHelper.writeData(addedItems, getActivity(), "listinfo2.dat");
                    Toast.makeText(getActivity(), "Item Added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Item Already Added", Toast.LENGTH_SHORT).show();
                }
                searchView.setQuery("", false);
                searchView.clearFocus();

            }
        });

        selectedListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                addedItems.remove(position);
                listAdapter.notifyDataSetChanged();
                FileHelper.writeData(addedItems, getActivity(), "listinfo2.dat");
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                return false;
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
                if(databaseList.contains(query))
                {
                    searchingAdapter.getFilter().filter(query);
                }
                else
                {
                }
                return false;
            }
            public boolean onQueryTextChange(String newText)
            {
                if(newText == null || newText.equals(""))
                {
                    searchingListView.setVisibility(View.GONE);
                    searchButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    searchingListView.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                }
                searchingAdapter.getFilter().filter(newText);
                return false;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedItems.clear();
                listAdapter.notifyDataSetChanged();
                FileHelper.writeData(addedItems, getActivity(), "listinfo2.dat");
                Toast.makeText(getActivity(), "Cleared", Toast.LENGTH_SHORT).show();
            }
        });
        //Search button function: Search by addedItems, create new Fragment that displays the found recipes(will probably use DiscoverFragment)
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Recipe> searchArray = new ArrayList<Recipe>();
                ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();
                Recipe r = new Recipe("Eggs", new ArrayList<String>(Arrays.asList("Egg", "Butter")), R.drawable.test1, "Stir the eggs");
                recipeArray.add(r);
                r = new Recipe("Pork", new ArrayList<String>(Arrays.asList("Pork", "Butter")), R.drawable.test1, "Cook the pork");
                recipeArray.add(r);
                //Pull all recipes from database (recipeArray is a place holder) and cross reference added items with their ingredients
                for(Recipe recipe : recipeArray)
                {
                    if(recipe.getIngredientList().containsAll(addedItems))
                    {
                        searchArray.add(recipe);
                    }
                }

                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new SearchResultFragment(searchArray));
                ft.commit();

            }
        });


        return root;
    }
}
