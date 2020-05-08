package com.example.mixit.ui.discover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.widget.LinearLayout.LayoutParams;

import com.example.mixit.R;
import com.example.mixit.ui.recipes.Recipe;
import com.example.mixit.ui.recipes.RecipeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel discoverViewModel;
    public int count = 0;
    ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();
    SearchView searchView;
    ArrayList<Recipe> searchArray = new ArrayList<Recipe>();
    TableLayout ll;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel =
                ViewModelProviders.of(this).get(DiscoverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        ll = root.findViewById(R.id.layout);

        //Recipe array is information from database
        final Recipe r = new Recipe("Scrambled Eggs", new ArrayList<String>(Arrays.asList("Eggs", "Butter")), R.drawable.test1, "Stir the eggs");
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);
        recipeArray.add(r);

        searchView = root.findViewById(R.id.searchViewDiscover);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            public boolean onQueryTextSubmit(String query)
            {
                for(Recipe recipe : recipeArray)
                {
                    if(recipe.getTitle().toLowerCase().contains(query.toLowerCase()))
                    {
                        searchArray.add(recipe);
                    }
                }
                ll.removeAllViews();
                ll.addView(createButtons(searchArray));
                searchArray.clear();
                return false;
            }
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
        int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.searchView.findViewById(searchCloseButtonId);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manage this event.
                ll.removeAllViews();
                ll.addView(createButtons(recipeArray));
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
        });

        ll.addView(createButtons(recipeArray));

        return root;
    }

    public TableLayout createButtons(final ArrayList<Recipe> recipes)
    {
        TableLayout table = new TableLayout(this.getActivity());
        int totalButtons = recipes.size();
        int totalRows = (totalButtons/2)+1;
        for (int row = 0; row < totalRows; row++) {
            TableRow currentRow = new TableRow(this.getActivity());
            int buttonsPerRow = 2;
            if(totalButtons < 2)
            {
                buttonsPerRow = totalButtons;
            }
            totalButtons -= 2;
            for (int button = 0; button < buttonsPerRow; button++) {
                Button currentButton = new Button(this.getActivity());
                TableRow.LayoutParams p = new TableRow.LayoutParams();
                p.rightMargin = 20;
                p.bottomMargin = 10;
                p.topMargin = 10;
                currentButton.setLayoutParams(p);
                currentButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.nav_host_fragment, new RecipeFragment(recipeArray.get(count))).addToBackStack("Discover").commit();
                        recipes.clear();
                    }
                });
                currentButton.setText("Recipe " + count+1);
                currentButton.setHeight(480);
                currentButton.setWidth(480);

                Bitmap bp = BitmapFactory.decodeResource(getResources(), recipes.get(count).getResid());
                Bitmap resized = Bitmap.createScaledBitmap(bp, 300, 300, true);

                BitmapDrawable bdrawable = new BitmapDrawable(getContext().getResources(), resized);

                currentButton.setText(recipes.get(count).getTitle());
                currentButton.setTextColor(Color.WHITE);
                currentButton.setBackground(bdrawable);

                currentRow.addView(currentButton);
            }
            table.addView(currentRow);
        }
        TableRow emptyRow = new TableRow(this.getActivity());
        table.addView(emptyRow);

        return table;
    }
}
