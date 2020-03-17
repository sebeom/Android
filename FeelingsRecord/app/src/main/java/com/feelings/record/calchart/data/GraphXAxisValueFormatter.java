package com.feelings.record.calchart.data;

import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class GraphXAxisValueFormatter implements XAxisValueFormatter {
    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        viewPortHandler.setMinimumScaleX(4f);
        if(index == 0){
            return "";
        }
        return index+"일";
    }
}
