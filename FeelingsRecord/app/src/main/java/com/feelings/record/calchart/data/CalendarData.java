package com.feelings.record.data;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.HashMap;
import java.util.HashSet;

public class CalendarData extends HashMap{

    public static final int VERY_HAPPY=5;
    public static final int HAPPY=4;
    public static final int NORMAL=3;
    public static final int BAD=2;
    public static final int HORRIBLE=1;


    private CalendarDay calDay;
    private int feeling;

    private HashSet<CalendarData> dates;

    public CalendarData(CalendarDay day, int color){
        this.calDay=day;
        this.feeling=color;
    }
    public CalendarDay getCalendarData(){
        return calDay;
    }
    public int getFeeling(){
        return feeling;
    }
    public int hashCode() {
        Log.d("hashCode" , ""+calDay.hashCode());
        return calDay.hashCode();
    }

    public boolean equals(@Nullable Object o) {
        return (hashCode() == o.hashCode() ? true : false);
    }
    public int getFeelColor(){
        int color=1;
        switch (feeling){
            case CalendarData.VERY_HAPPY:
                color= Color.parseColor("#5FB404");
                break;
            case CalendarData.HAPPY:
                color= Color.parseColor("#FE9A2E");
                break;
            case CalendarData.NORMAL:
                color= Color.parseColor("#F79F81");
                break;
            case CalendarData.BAD:
                color= Color.parseColor("#FA5858");
                break;
            case CalendarData.HORRIBLE:
                color= Color.parseColor("#6E6E6E");
                break;
        }

        return color;
    }
    public boolean equalsMonth(int month){
        return (calDay.getMonth() == (month) ? true : false );
    }
    public boolean equalsYear(int year){
        return (calDay.getYear() == (year) ? true : false );
    }

}
