package com.example.mixit.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mixit.R;
import com.example.mixit.ui.discover.DBHelper;
import com.example.mixit.ui.home.SearchResultFragment;
import com.example.mixit.ui.login.ChangePasswordActivity;
import com.example.mixit.ui.login.DatabaseHelper;
import com.example.mixit.ui.login.LoginActivity;
import com.example.mixit.ui.login.RegisterActivity;
import com.example.mixit.ui.recipes.Recipe;
import com.example.mixit.ui.recipes.RecipeFragment;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    AccountViewModel accountViewModel = new AccountViewModel();
    private boolean shouldRefreshOnResume = false;
    View root;
    DatabaseHelper loginDB;
    DBHelper recipeDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        root = inflater.inflate(R.layout.fragment_account, container, false);

        updateAccountFragment();
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(shouldRefreshOnResume){
        updateAccountFragment();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }

    public void updateAccountFragment()
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        Boolean loggedIn = pref.getBoolean("loggedIn", false);
        final String username = pref.getString("username", "DEFAULT");
        System.out.println("My username: "+ username);

        final Button loginButton = root.findViewById(R.id.login_button);
        final Button registerButton = root.findViewById(R.id.register_button);
        final Button logoutButton = root.findViewById(R.id.logout_button);
        final Button favoritesButton = root.findViewById(R.id.favorite_button);
        final Button accountChangePassword = root.findViewById(R.id.account_change_btn);

        loginDB = new DatabaseHelper(getContext());
        recipeDB = new DBHelper(getContext(), "recipeList.db", null, 1);

        if(loggedIn == false) {

            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            favoritesButton.setVisibility(View.GONE);
            accountChangePassword.setVisibility(View.GONE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(myIntent);
                }
            });
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(myIntent);
                }
            });
        }
        else
        {
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            favoritesButton.setVisibility(View.VISIBLE);
            accountChangePassword.setVisibility(View.VISIBLE);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean loggedIn = pref.edit().putBoolean("loggedIn", false).commit();
                    pref.edit().putString("username", "DEFAULT").commit();
                    updateAccountFragment();
                }
            });
            favoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Integer> recipeIDs = loginDB.getIdFavorites(username);
                    ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
                    for(int id : recipeIDs)
                    {
                        recipeList.add(recipeDB.recipes_SelectById(id));
                    }

                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, new SearchResultFragment(recipeList)).addToBackStack("Favorites").commit();
                }
            });

            accountChangePassword.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(myIntent);
                }
            });
        }
    }
}
