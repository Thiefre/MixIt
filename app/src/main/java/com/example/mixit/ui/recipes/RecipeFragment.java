package com.example.mixit.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;

public class RecipeFragment extends Fragment{
    private RecipeViewModel recipeViewModel;
    private Recipe recipe;

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

        title.setText(recipe.getTitle());
        ingredients.setText(recipe.ingredientsToString());
        instructions.setText(recipe.getInstructions());

        return root;
    }
}
