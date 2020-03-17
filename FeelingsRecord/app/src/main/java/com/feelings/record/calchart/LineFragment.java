package com.feelings.record.calchart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.feelings.record.R;
import com.feelings.record.calchart.data.CalendarData;
import com.feelings.record.calchart.data.GraphXAxisValueFormatter;
import com.feelings.record.calchart.data.GraphYAxisValueFormatter;
import com.feelings.record.calchart.data.LineValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineFragment extends Fragment {

    LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_line,container,false);
        setLayout(v);
        return v;
    }
    private void setLayout(View v){
        lineChart = v.findViewById(R.id.chart);
    }
    public void createLineChart(List<CalendarData> calendarDays){
        lineChart.invalidate();
        lineChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();

        int minDay=31;
        int maxDay=0;
        ArrayList<Integer> colors = new ArrayList<>();
        for(CalendarData data : calendarDays){
            entries.add(new Entry(data.getFeeling(),data.getCalendarData().getDay()));
            colors.add(data.getFeelColor());
            if(minDay > data.getCalendarData().getDay()) minDay = data.getCalendarData().getDay();
            if(maxDay < data.getCalendarData().getDay()) maxDay = data.getCalendarData().getDay();
        }

        LineDataSet dataSet = new LineDataSet(entries,"기분 변환표");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0;i<=maxDay;i++){
            labels.add(i+"");
        }

        LineData data = new LineData(labels, dataSet);
        dataSet.setColor(Color.LTGRAY); //
        dataSet.setDrawFilled(true); //그래프 밑부분 색칠
        dataSet.setCircleColors(colors);
        dataSet.setValueFormatter(new LineValueFormatter());

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new GraphXAxisValueFormatter());

        lineChart.getAxisLeft().setValueFormatter(new GraphYAxisValueFormatter());
        lineChart.getAxisRight().setEnabled(false);

        if(calendarDays ==null || calendarDays.isEmpty()){
            lineChart.setDescription("데이터가 없습니다");
            lineChart.setDescriptionTextSize(10f);
        }else{
            lineChart.setDescription("");
        }

        lineChart.setData(data);
        lineChart.animateY(2000);

    }
}
