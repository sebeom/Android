package com.feelings.record.calchart;

import com.feelings.record.calchart.data.CalendarData;

import java.util.List;

public interface CalendarDataListener {
    public void onDataTransfer(List<CalendarData> calendarDays, int[] feels);
}
