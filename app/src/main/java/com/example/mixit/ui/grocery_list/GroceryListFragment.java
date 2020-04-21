package com.example.mixit.ui.grocery_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.R;

public class GroceryListFragment extends Fragment {

    private GroceryListViewModel groceryListViewModel;
    public static final String LAST_TEXT = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groceryListViewModel =
                ViewModelProviders.of(this).get(GroceryListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        final TextView textView = root.findViewById(R.id.text_grocery_list);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        textView.setText(pref.getString(LAST_TEXT, ""));
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                pref.edit().putString(LAST_TEXT, s.toString()).commit();


            }
        });
        groceryListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}
