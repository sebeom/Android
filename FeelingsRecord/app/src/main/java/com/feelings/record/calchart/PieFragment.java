package com.feelings.record.calchart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.feelings.record.calchart.data.PieValueFormatter;
import com.feelings.record.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class PieFragment extends Fragment {

    PieChart pieChart;
    final int[] FEEL_COLOR=new int[]{Color.rgb(95,180,4),Color.rgb(254,154,46),
            Color.rgb(247,159,129),Color.rgb(250,80,80),Color.rgb(110,110,110)};;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie,container,false);

        pieChart = v.findViewById(R.id.piechart);


        return v;
    }
    public void createPieChart(int[] feels){
        pieChart.clear();

        ArrayList<Entry> NoOfEmp = new ArrayList<>();
        ArrayList<String> year = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        int total = 0;

            for (int i = 0; i < feels.length; i++) {
                if (feels[i] == 0) {
                    continue;
                }
                year.add(createChartText(i));
                NoOfEmp.add(new Entry(feels[i], i));
                colors.add(FEEL_COLOR[4 - i]);
                total=total+feels[i];
            }
            if(NoOfEmp.isEmpty()) {
                year.add("데이터 없음");
                NoOfEmp.add(new Entry(1, 0));
                colors.add(Color.rgb(150, 150, 150));
            }

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "  [ 이달의 기분 통계 ]");
        PieData data = new PieData(year, dataSet);          // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        dataSet.setColors(colors);
        dataSet.setValueFormatter(new PieValueFormatter(total));
        pieChart.animateXY(2000, 2000);
    }
    private String createChartText(int n){
        String str="";
        switch (n){
            case 4:
                str = "정말 좋음";
                break;
            case 3:
                str = "좋음";
                break;
            case 2:
                str = "보통";
                break;
            case 1:
                str = "나쁨";
                break;
            case 0:
                str = "끔찍함";
                break;

        }
        return str;
    }
}