package com.feelings.record.calchart;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.feelings.cal.data.CalendarData;
import com.feelings.cal.decorator.EventDecorator;
import com.feelings.cal.view.FeelDisplayView;
import com.feelings.record.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

public class FeelFragment extends Fragment implements CalendarDataListener{

    private HashMap<Integer,FeelDisplayView> feelViews;
    private ArrayList<CalendarData> dataArr;

    private CalendarDataListener listener;

    MaterialCalendarView materialCalendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feel,container,false);

        setLayout(v);
        return v;
    }
    private void setLayout(View v){
        feelViews = new HashMap<>();
        feelViews.put(CalendarData.VERY_HAPPY,(FeelDisplayView)v.findViewById(R.id.chart_very_happy));
        feelViews.put(CalendarData.HAPPY,(FeelDisplayView)v.findViewById(R.id.chart_happy));
        feelViews.put(CalendarData.NORMAL,(FeelDisplayView)v.findViewById(R.id.chart_normal));
        feelViews.put(CalendarData.BAD,(FeelDisplayView)v.findViewById(R.id.chart_bad));
        feelViews.put(CalendarData.HORRIBLE,(FeelDisplayView)v.findViewById(R.id.chart_horrible));

        materialCalendarView = (MaterialCalendarView)v.findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                displayCalendarChart(date);
            }
        });
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
    }

    @Override
    public void onDataTransfer(List<CalendarData> calendarDays,int[] feels) {
        listener.onDataTransfer(calendarDays,feels);
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarData>> {

        ArrayList<String> Time_Result;

        ApiSimulator(ArrayList<String> Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarData> doInBackground(@NonNull Void... voids) {

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarData> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(String str : Time_Result){
                String[] time = str.split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                int type = Integer.parseInt(time[3]);

                calendar.set(year,month-1,dayy);

                CalendarDay day = CalendarDay.from(calendar);
                dates.add(new CalendarData(day,type));
            }
            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarData> calendarDays) {
            super.onPostExecute(calendarDays);
            dataArr = new ArrayList<>(calendarDays);
            for(int i=CalendarData.HORRIBLE;i<=CalendarData.VERY_HAPPY;i++){
                materialCalendarView.addDecorator(distinctionCalender(calendarDays,i));
            }
            displayCalendarChart(CalendarDay.today());


        }
    }
    public void putCalendarValue(ArrayList<String> str){
        addCalendarDraw(str);
    }
    private void addCalendarDraw(ArrayList<String> dateStr){
        new ApiSimulator(dateStr).executeOnExecutor(Executors.newSingleThreadExecutor());
    }
    private EventDecorator distinctionCalender(List<CalendarData> calendarDays, int isFeel){
        ArrayList<CalendarData> list = new ArrayList<CalendarData>();

        Iterator iter = calendarDays.iterator();
        CalendarData data;
        while(iter.hasNext()){
            data = (CalendarData)iter.next();
            if(data.getFeeling() == isFeel){
                list.add(data);
            }
        }
        return new EventDecorator(list,isFeel);
    }
    private void displayCalendarChart(CalendarDay date){
        if(!dataArr.isEmpty()){
            int[] feels = new int[5];
            ArrayList<CalendarData> tempArr = new ArrayList<>();
            for(CalendarData data : dataArr){
                if(data.equalsMonth(date.getMonth()) && data.equalsYear(date.getYear())){
                    feels[data.getFeeling()-1]++;
                    tempArr.add(data);
                }
            }
            feelViews.get(CalendarData.VERY_HAPPY).setFeelText(feels[4]+"");
            feelViews.get(CalendarData.HAPPY).setFeelText(feels[3]+"");
            feelViews.get(CalendarData.NORMAL).setFeelText(feels[2]+"");
            feelViews.get(CalendarData.BAD).setFeelText(feels[1]+"");
            feelViews.get(CalendarData.HORRIBLE).setFeelText(feels[0]+"");

            onDataTransfer(tempArr,feels);
        }
    }
    public void setCalendarDataListener(CalendarDataListener listener){
        this.listener=listener;
    }
}