package com.example.mixit.ui.recipes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;
import com.example.mixit.ui.login.DatabaseHelper;

import java.util.ArrayList;

public class RecipeFragment extends Fragment{
    private RecipeViewModel recipeViewModel;
    private Recipe recipe;

    DatabaseHelper loginDB;

    Button backButton;
    Button addFavorite;
    Button removeFavorite;

    public RecipeFragment(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        TextView title = (TextView)root.findViewById(R.id.recipe_title);
        TextView ingredients = (TextView) root.findViewById(R.id.recipe_ingredients);
        TextView instructions = (TextView) root.findViewById(R.id.recipe_instructions);

        title.setText("Title: "+ recipe.getTitle());
        ingredients.setText("Ingredients: "+ recipe.ingredientsToString());
        instructions.setText("Instructions: " + recipe.getInstructions());

        backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        loginDB = new DatabaseHelper(getContext());

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        Boolean loggedIn = pref.getBoolean("loggedIn", false);
        final String username = pref.getString("username", "DEFAULT");
        if(loggedIn) {

            ArrayList<Integer> favoriteIDs = loginDB.getIdFavorites(username);
            System.out.println("Favorite IDS: " + favoriteIDs.toString());

            addFavorite = root.findViewById(R.id.add_favorite_btn);
            removeFavorite = root.findViewById(R.id.remove_favorite_btn);

            if(!favoriteIDs.contains(recipe.getId())) {
                addFavorite.setVisibility(View.VISIBLE);
                addFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginDB.addFav(username, recipe.getId());
                        addFavorite.setVisibility(View.GONE);
                        removeFavorite.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                removeFavorite.setVisibility(View.VISIBLE);
                removeFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginDB.removeFav(username, recipe.getId());
                        addFavorite.setVisibility(View.VISIBLE);
                        removeFavorite.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return root;
    }
}
