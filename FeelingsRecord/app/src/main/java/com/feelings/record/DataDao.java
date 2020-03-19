package com.feelings.record;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {
    @Insert
    long insert(Data data);

    @Update
    int update(Data data);

    /*@Query("DELETE FROM data")
    int deleteAll();*/

    @Query("DELETE FROM data WHERE id = :id")
    int delete(int id);
    /*@Query("DELETE FROM data WHERE id = :id")
    int deleteData(int id);*/

    @Query("SELECT * from data ORDER BY id ASC")
    LiveData<List<Data>> getAllDatas();
}
