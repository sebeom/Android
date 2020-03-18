package com.feelings.record;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data")
public class Data implements Parcelable {

    public static final int VERY_HAPPY=5;
    public static final int HAPPY=4;
    public static final int NORMAL=3;
    public static final int BAD=2;
    public static final int HORRIBLE=1;

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

    Data(Parcel parcel){
        id = parcel.readInt();
        content = parcel.readString();
        imageview = parcel.readString();
        date = parcel.readString();
        time = parcel.readString();
        mood = parcel.readInt();
    }
    Data(){}

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

    public static final Parcelable.Creator<Data> CREATOR =new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeString(this.imageview);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeInt(this.mood);
    }
}
