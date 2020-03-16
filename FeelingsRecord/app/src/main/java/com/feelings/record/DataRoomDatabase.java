package com.feelings.record;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Data.class}, version = 1)
public abstract class DataRoomDatabase extends RoomDatabase {

    public abstract DataDao dataDao();

    public static volatile DataRoomDatabase INSTANCE;

    static DataRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DataRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataRoomDatabase.class, "data_database").build();

                }
            }
        }
        return INSTANCE;
    }
}
