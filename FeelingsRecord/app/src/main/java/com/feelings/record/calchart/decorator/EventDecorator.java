package com.feelings.record.calchart.decorator;

import android.graphics.Color;

import com.feelings.record.calchart.data.CalendarData;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {

    private ArrayList<CalendarData> dates;
    private int color;

    public EventDecorator(Collection<CalendarData> dates,int isFeel) {
        this.dates = new ArrayList<>(dates);
        color = getFeelColor(isFeel);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Iterator iter = dates.iterator();
        CalendarDay tempDay;
        while(iter.hasNext()){
            tempDay= ((CalendarData) iter.next()).getCalendarData();
            if(tempDay.equals(day)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new CustomMultipleDotSpan(5f,color)); // 날자밑에 점
    }
    public int getFeelColor(int feelId){
        int color=1;
        switch (feelId){
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
}