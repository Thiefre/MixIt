package com.example.mixit.ui.grocery_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mixit.FileHelper;
import com.example.mixit.R;

import java.util.ArrayList;

public class GroceryListFragment extends Fragment {

    private GroceryListViewModel groceryListViewModel;
    private EditText itemET;
    private Button btn;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groceryListViewModel =
                ViewModelProviders.of(this).get(GroceryListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        itemET = root.findViewById(R.id.item_edit_text);
        btn = root.findViewById(R.id.add_btn);
        itemsList = root.findViewById(R.id.items_list);
        items = FileHelper.readData(getActivity(), "listinfo.dat");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                switch(v.getId()){
                    case R.id.add_btn:
                        String itemEntered = itemET.getText().toString();
                        if(!itemEntered.trim().isEmpty()) {
                            adapter.add(itemEntered);
                            itemET.setText("");
                            FileHelper.writeData(items, getActivity(), "listinfo.dat");
                            Toast.makeText(getActivity(), "Item Added", Toast.LENGTH_SHORT).show();
                            break;
                        }
                }
            }});
        itemsList.setOnItemLongClickListener(new ListView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                adapter.notifyDataSetChanged();
                FileHelper.writeData(items, getActivity(), "listinfo.dat");
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return root;
    }
}
