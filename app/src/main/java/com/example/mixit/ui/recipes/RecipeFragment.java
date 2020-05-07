package com.example.mixit.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;

public class RecipeFragment extends Fragment{
    private RecipeViewModel recipeViewModel;
    private Recipe recipe;
    Button backButton;

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

        return root;
    }
}
