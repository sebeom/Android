package com.feelings.record.data;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class LineValueFormatter implements ValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return transferFeelText((int)value);
    }
    private String transferFeelText(int value){
        String str="";
        switch (value){
            case CalendarData.VERY_HAPPY:
                str = "정말 좋음";
                break;
            case CalendarData.HAPPY:
                str = "좋음";
                break;
            case CalendarData.NORMAL:
                str = "보통";
                break;
            case CalendarData.BAD:
                str = "나쁨";
                break;
            case CalendarData.HORRIBLE:
                str = "끔찍함";
                break;
        }
        return str;
    }
}
