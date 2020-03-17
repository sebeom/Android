package com.feelings.record.data;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

public class GraphYAxisValueFormatter implements YAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        yAxis.setYOffset(0.1f);
        yAxis.calcMinMax(1f,5f);
        yAxis.setLabelCount(5,false);
        yAxis.setShowOnlyMinMax(false);
        YAxis.YAxisLabelPosition pos = yAxis.getLabelPosition();
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
