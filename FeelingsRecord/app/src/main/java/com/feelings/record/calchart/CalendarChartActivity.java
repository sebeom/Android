package com.feelings.record.calchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.feelings.record.Data;
import com.feelings.record.DataRepository;
import com.feelings.record.R;
import com.feelings.record.calchart.data.CalendarData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalendarChartActivity extends AppCompatActivity implements CalendarDataListener{

    private FeelFragment calendarFragment;
    private PieFragment pieFragment;
    private LineFragment lineFragment;


    private DataRepository repository;
    private LiveData<List<Data>> allDatas;

    private ArrayList<String> valueArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_chart);
        setLayout();
        getDataBase();
    }


    @Override
    public void onDataTransfer(List<CalendarData> calendarDays, int[] feels) {
        //pieFragment , lineFragment data 전송
        pieFragment.createPieChart(feels);
        lineFragment.createLineChart(calendarDays);
    }
    private void setLayout(){
        FragmentManager manager = getSupportFragmentManager();
        pieFragment = (PieFragment)manager.findFragmentById(R.id.fragmentPie);
        lineFragment = (LineFragment)manager.findFragmentById(R.id.fragmentLine);
        calendarFragment = (FeelFragment)manager.findFragmentById(R.id.fragmentCalendar);
    }
    private void getDataBase(){
        valueArr = new ArrayList<>();
        //String[] str = {"2020,03,8,5","2019,04,18,4","2020,03,20,3","2020,03,21,2","2020,03,22,1","2020,03,23,3"};

        repository = new DataRepository(getApplication());
        allDatas = repository.getAllDatas();

        allDatas.observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                for (Data d : data){
                    valueArr.add(d.getDate()+","+d.getMood());
                }
                calendarFragment.putCalendarValue(valueArr);
                calendarFragment.setCalendarDataListener(CalendarChartActivity.this);
            }
        });
    }
}
