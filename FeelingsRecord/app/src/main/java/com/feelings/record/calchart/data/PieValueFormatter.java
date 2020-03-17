package com.feelings.record.data;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class PieValueFormatter implements ValueFormatter {
    float totalPer;
    public PieValueFormatter(int total){
        if(total==0)  totalPer=0;
        else this.totalPer = 100/total;
    }
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if(totalPer==0)return "";
        return (totalPer*value)+"% ("+(int)value+")";
    }
}
