package com.ominext.kcpcalendar.listener;

import java.util.Date;

/**
 * Created by Vin on 10/23/2016.
 */
public interface CalendarSelectionListener {
    void onDateSelected(Date date);
    void onMonthChanged(Date date);
}
