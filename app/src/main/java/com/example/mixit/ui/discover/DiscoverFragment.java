package com.example.mixit.ui.discover;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.widget.LinearLayout.LayoutParams;

import com.example.mixit.R;

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel discoverViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel =
                ViewModelProviders.of(this).get(DiscoverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discover, container, false);
        TableLayout ll = (TableLayout)root.findViewById(R.id.layout);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        Button[][] buttonArray = new Button[4][4];
        TableLayout table = new TableLayout(this.getActivity());
        int count = 0;
        for (int row = 0; row < 4; row++) {
            TableRow currentRow = new TableRow(this.getActivity());
            for (int button = 0; button < 4; button++) {
                Button currentButton = new Button(this.getActivity());
                currentButton.setText("Recipe " + ++count);
                currentButton.setHeight(300);
                currentButton.setWidth(250);

                buttonArray[row][button] = currentButton;
                currentRow.addView(currentButton);
            }
            table.addView(currentRow);
        }
        ll.addView(table);

        return root;
    }
}
