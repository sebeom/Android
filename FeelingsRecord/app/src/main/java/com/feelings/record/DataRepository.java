package com.feelings.record;

import android.app.Application;

import androidx.lifecycle.LiveData;

class DataRepository {

    private final DataDao dataDao;

    DataRepository(Application application){
        DataRoomDatabase db = DataRoomDatabase.getDatabase(application);
        dataDao = db.dataDao();


    }
}
