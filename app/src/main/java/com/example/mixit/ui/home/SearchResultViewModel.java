package com.example.mixit.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchResultViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchResultViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is result fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}