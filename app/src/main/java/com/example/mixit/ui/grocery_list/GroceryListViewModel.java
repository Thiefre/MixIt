package com.example.mixit.ui.grocery_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroceryListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GroceryListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}