package com.example.grocery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;

public class Grocery extends Fragment {

    private EditText itemET;
    private Button btn;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    public View createView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_grocery, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        itemET = getView().findViewById(R.id.item_edit_text);
        btn = getView().findViewById(R.id.add_btn);
        itemsList = getView().findViewById(R.id.items_list);
        items = FileHelper.readData(getActivity());
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
            switch(v.getId()){
                case R.id.add_btn:
                    String itemEntered = itemET.getText().toString();
                    adapter.add(itemEntered);
                    itemET.setText("");
                    FileHelper.writeData(items, getActivity());
                    Toast.makeText(getActivity(), "Item Added", Toast.LENGTH_SHORT).show();
                    break;
            }
        }});
        itemsList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                adapter.notifyDataSetChanged();
                FileHelper.writeData(items, getActivity());
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
