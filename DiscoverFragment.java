package com.example.discover;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment {
    EditText e1;
    Button b1;
    CardView card;
    private ArrayList<RecipeItem> item;
    private ArrayList<RecipeItem> randomList;
    private DBHelper db;
    private ImageHelper imageHelper = new ImageHelper();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        return v;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        e1 = (EditText) getView().findViewById(R.id.rName);
        b1 = (Button) getView().findViewById(R.id.addbtn);
        card = (CardView) getView().findViewById(R.id.cardView);
        db = new DBHelper(getContext() , "recipeList.db", null , 1);

        //adding recipes to database need to be done in mainactivity of the app
//        Drawable drawable = getResources().getDrawable(R.drawable.app1, getActivity().getTheme());
//        byte[] app1 = imageHelper.getByteArrayFromDrawable(drawable);
//        db.recipeInsert(123, "eggs", "eggs milk", "throw", app1, app1, 0);
//        drawable = getResources().getDrawable(R.drawable.app2, getActivity().getTheme());
//        byte[] app2 = imageHelper.getByteArrayFromDrawable(drawable);
//        db.recipeInsert(123, "eggs", "eggs milk", "throw", app1, app1, 0);

        b1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = e1.getText().toString();
                System.out.println(db.getCount());
                item = db.recipes_SelectByName(name);
                System.out.println("array" + item.size());
            }
        });

        //random recipes
        randomList = new ArrayList<>();
        randomList=db.getRecipeRandom();

    }
}
