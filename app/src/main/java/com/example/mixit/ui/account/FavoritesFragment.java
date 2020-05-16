package com.example.mixit.ui.account;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;
import com.example.mixit.ui.discover.DBHelper;
import com.example.mixit.ui.discover.ImageHelper;
import com.example.mixit.ui.home.SearchResultViewModel;
import com.example.mixit.ui.login.DatabaseHelper;
import com.example.mixit.ui.recipes.Recipe;
import com.example.mixit.ui.recipes.RecipeButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private int count = 0;
    private ScrollView scrollView;
    private DatabaseHelper loginDB;
    private String username;
    private DBHelper recipeDB;

    public FavoritesFragment(DBHelper recipeDB, DatabaseHelper loginDB, String username) {
        this.recipeDB = recipeDB;
        this.loginDB = loginDB;
        this.username = username;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_results, container, false);
        scrollView = root.findViewById(R.id.searchResultsLayout);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Integer> recipeIDs = loginDB.getIdFavorites(username);
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        for(int id : recipeIDs)
        {
            recipeList.add(recipeDB.recipes_SelectById(id));
        }
        scrollView.removeAllViews();
        scrollView.addView(createButtons(recipeList));
    }

    public TableLayout createButtons(final ArrayList<Recipe> recipes) {
        TableLayout.LayoutParams tp = new TableLayout.LayoutParams();
        TableLayout table = new TableLayout(this.getActivity());

        tp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        tp.height = LinearLayout.LayoutParams.MATCH_PARENT;

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
                Recipe currentRecipe = recipes.get(count);
                RecipeButton currentButton = new RecipeButton(this.getActivity());
                TableRow.LayoutParams p = new TableRow.LayoutParams();
                p.rightMargin = 20;
                p.bottomMargin = 10;
                p.topMargin = 10;
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
