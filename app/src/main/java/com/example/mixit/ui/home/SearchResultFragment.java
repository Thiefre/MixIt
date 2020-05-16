package com.example.mixit.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;
import com.example.mixit.ui.discover.ImageHelper;
import com.example.mixit.ui.recipes.Recipe;
import com.example.mixit.ui.recipes.RecipeButton;
import com.example.mixit.ui.recipes.RecipeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResultFragment extends Fragment {

    private SearchResultViewModel searchResultViewModel;
    private int count = 0;
    private ArrayList<Recipe> recipeArray;
    private ScrollView scrollView;

    public SearchResultFragment(ArrayList<Recipe> recipes)
    {
        recipeArray = recipes;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchResultViewModel =
                ViewModelProviders.of(this).get(SearchResultViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_results, container, false);


        scrollView = root.findViewById(R.id.searchResultsLayout);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        scrollView.removeAllViews();
        scrollView.addView(createSearchButtons());
    }

    public TableLayout createSearchButtons()
    {
        TableLayout.LayoutParams tp = new TableLayout.LayoutParams();
        TableLayout table = new TableLayout(this.getActivity());

        tp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tp.height = LinearLayout.LayoutParams.MATCH_PARENT;

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
                Recipe currentRecipe = recipeArray.get(count);
                RecipeButton currentButton = new RecipeButton(this.getActivity());
                TableRow.LayoutParams p = new TableRow.LayoutParams();
                p.rightMargin = 20;
                p.bottomMargin = 20;
                currentButton.setLayoutParams(p);
                currentButton.setRecipeListener(currentRecipe, getParentFragmentManager());
                currentButton.setHeight(450);
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                int buttonWidth = (int)(width/2.2);
                currentButton.setWidth(buttonWidth);
                Picasso.get()
                        .load(currentRecipe.getResid())
                        .resize(buttonWidth,450)
                        .into(currentButton);

                currentButton.setText(currentRecipe.getTitle());
                currentButton.setTextColor(Color.WHITE);
                currentButton.setTextSize(20);

                currentRow.addView(currentButton);
                count++;
            }
            table.addView(currentRow);
        }
        count = 0;
        TableRow emptyRow = new TableRow(this.getActivity());
        table.addView(emptyRow);

        return table;
    }
}
