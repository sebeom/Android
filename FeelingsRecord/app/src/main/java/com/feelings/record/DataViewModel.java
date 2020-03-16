package com.feelings.record;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class DataViewModel extends AndroidViewModel {
    private static final String TAG = DataViewModel.class.getSimpleName();

    private final DataRepository repository;
    private final LiveData<List<Data>> allDatas;

    public DataViewModel(Application application){
        super(application);
        repository = new DataRepository(application);
        allDatas = repository.getAllDatas();
    }

    public LiveData<List<Data>> getAllDatas(){
        return allDatas;
    }

    public void insert(Data data){ repository.insert(data); }
}