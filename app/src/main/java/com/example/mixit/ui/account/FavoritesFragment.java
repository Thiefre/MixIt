package com.example.mixit.ui.account;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ImageHelper imageHelper = new ImageHelper();
    private TableLayout ll;
    private DatabaseHelper loginDB;
    private String username;
    private DBHelper recipeDB;

    public FavoritesFragment(DBHelper recipeDB, DatabaseHelper loginDB, String username) {
        this.recipeDB = recipeDB;
        this.username = username;
        this.loginDB = loginDB;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_results, container, false);


        ll = (TableLayout)root.findViewById(R.id.searchResultsLayout);
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
        ll.removeAllViews();
        ll.addView(createSearchButtons(recipeList));
    }

    public TableLayout createSearchButtons(final ArrayList<Recipe> recipeArray)
    {
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
                RecipeButton currentButton = new RecipeButton(this.getActivity());
                TableRow.LayoutParams p = new TableRow.LayoutParams();
                p.rightMargin = 20;
                p.bottomMargin = 20;
                currentButton.setLayoutParams(p);
                System.out.print("Recipes: ");
                for(Recipe r: recipeArray)
                {
                    System.out.print(r.getTitle()+", ");
                }
                System.out.println("");
                currentButton.setRecipeListener(recipeArray.get(count), getParentFragmentManager());
                currentButton.setHeight(480);
                currentButton.setWidth(480);

//                Bitmap bp = imageHelper.getBitmapFromByteArray(recipeArray.get(count).getThumbnail());
//                Bitmap resized = Bitmap.createScaledBitmap(bp, 300, 300, true);
//
//                BitmapDrawable bdrawable = new BitmapDrawable(getContext().getResources(), resized);

                currentButton.setText(recipeArray.get(count).getTitle());
                currentButton.setTextColor(Color.WHITE);
                Picasso.get()
                        .load(recipeArray.get(count).getResid())
                        .resize(480,480)
                        .into(currentButton);

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
