package com.example.mixit.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mixit.MainActivity;
import com.example.mixit.R;
import com.example.mixit.ui.discover.DiscoverViewModel;
import com.example.mixit.ui.login.Login;
import com.example.mixit.ui.login.Register;


public class AccountFragment extends Fragment {

    AccountViewModel accountViewModel = new AccountViewModel();
    private boolean shouldRefreshOnResume = false;
    View root;

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

        final Button loginButton = root.findViewById(R.id.login_button);
        final Button registerButton = root.findViewById(R.id.register_button);
        final Button logoutButton = root.findViewById(R.id.logout_button);

        if(loggedIn == false) {

            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), Login.class);
                    startActivity(myIntent);
                }
            });
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), Register.class);
                    startActivity(myIntent);
                }
            });
        }
        else
        {
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean loggedIn = pref.edit().putBoolean("loggedIn", false).commit();
                    updateAccountFragment();
                }
            });
        }
    }
}
