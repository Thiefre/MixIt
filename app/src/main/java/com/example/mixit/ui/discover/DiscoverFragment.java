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
import android.widget.LinearLayout;
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
    public ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel =
                ViewModelProviders.of(this).get(DiscoverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        TableLayout ll = (TableLayout)root.findViewById(R.id.layout);

        final Recipe r = new Recipe("Scrambled Eggs", new ArrayList<String>(Arrays.asList("Eggs", "Butter")), R.drawable.test1, "Stir the eggs");
        recipeArray.add(r);
        recipeArray.add(r);


        TableLayout table = new TableLayout(this.getActivity());
        int totalButtons = recipeArray.size();
        int totalRows = (totalButtons/3)+1;
        for (int row = 0; row < totalRows; row++) {
            TableRow currentRow = new TableRow(this.getActivity());
            int buttonsPerRow = 3;
            if(totalButtons < 3)
            {
                buttonsPerRow = totalButtons;
            }
            totalButtons -= 3;
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
                        RecipeFragment fragment = new RecipeFragment(recipeArray.get(count));
                        ft.replace(R.id.nav_host_fragment, fragment);
                        ft.commit();
                    }
                });
                currentButton.setText("Recipe " + count+1);
                currentButton.setHeight(300);
                currentButton.setWidth(300);

                Bitmap bp = BitmapFactory.decodeResource(getResources(), recipeArray.get(count).getResid());
                Bitmap resized = Bitmap.createScaledBitmap(bp, 300, 300, true);

                BitmapDrawable bdrawable = new BitmapDrawable(getContext().getResources(), resized);

                currentButton.setText(recipeArray.get(count).getTitle());
                currentButton.setTextColor(Color.WHITE);
                currentButton.setBackground(bdrawable);

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
