package com.ominext.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ominext.sample.R;
import com.ominext.kcpcalendar.listener.CalendarSelectionListener;

import java.util.Date;
import java.util.HashMap;

public class KCPCalendarActivity extends Activity {
    com.ominext.kcpcalendar.KCPCalendar calendar;
    TextView monthTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcpcalendar);
        calendar = (com.ominext.kcpcalendar.KCPCalendar) findViewById(R.id.calendar);
        findViewById(R.id.nextIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.nextMonth();
            }
        });
        findViewById(R.id.prevIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.prevMonth();
            }
        });
        calendar.setCalendarSelectionListener(new CalendarSelectionListener() {
            @Override
            public void onDateSelected(Date date) {
                Toast.makeText(KCPCalendarActivity.this, "OnDateSelected: " + date.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthChanged(Date date) {
                Toast.makeText(KCPCalendarActivity.this,"OnMonthChanged: " + date.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        HashMap<String, com.ominext.kcpcalendar.models.DateConfig> dataConfig = new HashMap<>();
        dataConfig.put("2016-10-23", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-19", new com.ominext.kcpcalendar.models.DateConfig(true, false));
        dataConfig.put("2016-10-01", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-02", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-03", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-04", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-05", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-06", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-07", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-08", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-09", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-12-10", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-11", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-12", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-13", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-11-14", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-10-15", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        dataConfig.put("2016-11-16", new com.ominext.kcpcalendar.models.DateConfig(true, true));
        calendar.setListDateConfig(dataConfig);
    }
}
