package com.feelings.record;

import com.feelings.cal.data.CalendarData;

import java.util.List;

public interface CalendarDataListener {
    public void onDataTransfer(List<CalendarData> calendarDays, int[] feels);
}
