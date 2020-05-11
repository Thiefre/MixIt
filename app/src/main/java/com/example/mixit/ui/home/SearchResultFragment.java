package com.example.mixit.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;
import com.example.mixit.ui.discover.ImageHelper;
import com.example.mixit.ui.recipes.Recipe;
import com.example.mixit.ui.recipes.RecipeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultFragment extends Fragment {

    private SearchResultViewModel searchResultViewModel;
    public int count = 0;
    public ArrayList<Recipe> recipeArray;
    private ImageHelper imageHelper = new ImageHelper();

    public SearchResultFragment(ArrayList<Recipe> recipes)
    {
        recipeArray = recipes;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchResultViewModel =
                ViewModelProviders.of(this).get(SearchResultViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_results, container, false);


        TableLayout ll = (TableLayout)root.findViewById(R.id.searchResultsLayout);

        TableLayout table = new TableLayout(this.getActivity());
        int totalButtons = recipeArray.size();
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
                p.bottomMargin = 20;
                currentButton.setLayoutParams(p);
                currentButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.nav_host_fragment, new RecipeFragment(recipeArray.get(count))).addToBackStack("SearchResults").commit();
                    }
                });
                currentButton.setHeight(480);
                currentButton.setWidth(480);

//                Bitmap bp = imageHelper.getBitmapFromByteArray(recipeArray.get(count).getThumbnail());
//                Bitmap resized = Bitmap.createScaledBitmap(bp, 300, 300, true);
//
//                BitmapDrawable bdrawable = new BitmapDrawable(getContext().getResources(), resized);

                currentButton.setText(recipeArray.get(count).getTitle());
                currentButton.setTextColor(Color.WHITE);
//                currentButton.setBackground(bdrawable);

                currentRow.addView(currentButton);
            }
            table.addView(currentRow);
        }
        TableRow emptyRow = new TableRow(this.getActivity());
        table.addView(emptyRow);
        ll.addView(table);

        return root;
    }
}
