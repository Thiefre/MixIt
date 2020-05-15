package com.example.mixit.ui.discover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mixit.ui.recipes.RecipeButton;
import com.example.mixit.ui.recipes.RecipeFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel discoverViewModel;
    private DBHelper db;
    private ImageHelper imageHelper = new ImageHelper();
    private int count = 0;
    ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();
    SearchView searchView;
    ArrayList<Recipe> searchArray = new ArrayList<Recipe>();
    TableLayout ll;
    private RecipeButton currentButton;
    private Recipe currentRecipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel =
                ViewModelProviders.of(this).get(DiscoverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        ll = root.findViewById(R.id.layout);

        db = new DBHelper(getContext(), "recipeList.db", null, 1);

        //Recipe array is information from database
        recipeArray = db.randomRecipe();
        System.out.println("Recipe count from Discover Fragment "+ db.getCount());

        searchView = root.findViewById(R.id.searchViewDiscover);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            public boolean onQueryTextSubmit(String query)
            {
                searchArray = db.recipes_SelectByName(query.toLowerCase());
                ll.removeAllViews();
                try {
                    ll.addView(createButtons(searchArray));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                try {
                    ll.addView(createButtons(recipeArray));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
        });

        try {
            ll.addView(createButtons(recipeArray));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }


    public TableLayout createButtons(final ArrayList<Recipe> recipes) throws IOException {
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
                currentRecipe = recipes.get(count);
                currentButton = new RecipeButton(this.getActivity());
                TableRow.LayoutParams p = new TableRow.LayoutParams();
                p.rightMargin = 20;
                p.bottomMargin = 10;
                p.topMargin = 10;
                currentButton.setLayoutParams(p);
                currentButton.setRecipeListener(recipes.get(count), getParentFragmentManager());
                currentButton.setHeight(480);
                currentButton.setWidth(480);
                Picasso.get()
                        .load(currentRecipe.getResid())
                        .resize(480,480)
                        .into(currentButton);
//                Bitmap resized = Bitmap.createScaledBitmap(bit, 480, 480, true);

//                BitmapDrawable bdrawable = new BitmapDrawable(getContext().getResources(), resized);

                currentButton.setText(currentRecipe.getTitle());
                currentButton.setTextColor(Color.WHITE);

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
