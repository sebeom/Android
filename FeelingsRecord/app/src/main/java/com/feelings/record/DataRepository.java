package com.feelings.record;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private final DataDao dataDao;
    private final LiveData<List<Data>> allDatas;


    public DataRepository(Application application){
        DataRoomDatabase db = DataRoomDatabase.getDatabase(application);
        dataDao = db.dataDao();
        allDatas = dataDao.getAllDatas();

    }
    public LiveData<List<Data>> getAllDatas(){
        return allDatas;
    }

    public void insert(Data data, MutableLiveData<Boolean> flag){
        new InsertDBAsyncTask(flag, dataDao).execute(data);
    }
    public void delete(Data data, MutableLiveData<Boolean> flag){
        new DeleteDBAsyncTask(flag,dataDao).execute(data);
    }
    public void update(Data data, MutableLiveData<Boolean> flag){
        new UpdateDBAsyncTask(flag,dataDao).execute(data);
    }
}
class InsertDBAsyncTask extends AsyncTask<Data, Void, Long> {

    private MutableLiveData<Boolean> innerflag;
    private DataDao dataDao;

    public InsertDBAsyncTask(MutableLiveData<Boolean> flag, DataDao dataDao){
        this.innerflag = flag;
        this.dataDao = dataDao;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Long doInBackground(Data... datas) {
        if (dataDao == null)
            return -1L;
                /*String name = users[0].getUserName();
                int age = users[0].getAge();*/
//                return userDao.insertUser(name, age);
        return dataDao.insert(datas[0]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.d("TAG", "insert : " + aLong);
        innerflag.setValue(true);
    }

}
class DeleteDBAsyncTask extends AsyncTask<Data, Void, Long> {

    private MutableLiveData<Boolean> innerflag;
    private DataDao dataDao;

    public DeleteDBAsyncTask(MutableLiveData<Boolean> flag, DataDao dataDao){
        this.innerflag = flag;
        this.dataDao = dataDao;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Long doInBackground(Data... datas) {
        if (dataDao == null)
            return -1L;
                /*String name = users[0].getUserName();
                int age = users[0].getAge();*/
//                return userDao.insertUser(name, age);
        return (long)dataDao.delete(datas[0].getId());
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.d("TAG", "insert : " + aLong);
        innerflag.setValue(true);
    }

}
class UpdateDBAsyncTask extends AsyncTask<Data, Void, Long> {

    private MutableLiveData<Boolean> innerflag;
    private DataDao dataDao;

    public UpdateDBAsyncTask(MutableLiveData<Boolean> flag, DataDao dataDao){
        this.innerflag = flag;
        this.dataDao = dataDao;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Long doInBackground(Data... datas) {
        if (dataDao == null)
            return -1L;
        return (long)dataDao.update(datas[0]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Log.d("TAG", "update : " + aLong);
        innerflag.setValue(true);
    }

}
