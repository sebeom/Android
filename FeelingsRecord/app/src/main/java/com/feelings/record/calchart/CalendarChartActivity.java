package com.feelings.record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.feelings.record.data.CalendarData;

import java.util.List;

public class CalendarChartActivity extends AppCompatActivity implements CalendarDataListener{

    private FeelFragment calendarFragment;
    private PieFragment pieFragment;
    private LineFragment lineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] result = {"2020,03,8,5","2019,04,18,4","2020,03,20,3","2020,03,21,2","2020,03,22,1","2020,03,23,3"};
        FragmentManager manager = getSupportFragmentManager();

        pieFragment = (PieFragment)manager.findFragmentById(R.id.fragmentPie);
        calendarFragment = (FeelFragment)manager.findFragmentById(R.id.fragmentCalendar);
        lineFragment = (LineFragment)manager.findFragmentById(R.id.fragmentLine);

        calendarFragment.putCalendarValue(result);
        calendarFragment.setCalendarDataListener(this);
    }


    @Override
    public void onDataTransfer(List<CalendarData> calendarDays, int[] feels) {
        //pieFragment , lineFragment data 전송
        pieFragment.createPieChart(feels);
        lineFragment.createLineChart(calendarDays);
    }
}
