package com.feelings.record;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data")
public class Data {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private int id;
    @ColumnInfo
    private String content;
    @ColumnInfo
    private String imageview;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private String time;
    @ColumnInfo
    private int mood;

    Data(String imageview, String content){
        this.imageview = imageview;
        this.content = content;

    }
    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }
    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }
    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    @NonNull
    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }
}
