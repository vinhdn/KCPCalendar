package com.ominext.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ominext.kcpcalendar.listener.CalendarSelectionListener;
import com.ominext.sample.R;

import java.util.Date;
import java.util.HashMap;

public class SKPCalendarActivity extends Activity {
    com.ominext.kcpcalendar.SKPCalendar calendar;
    TextView monthTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skpcalendar);
        calendar = (com.ominext.kcpcalendar.SKPCalendar) findViewById(R.id.calendar);
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
                Toast.makeText(SKPCalendarActivity.this, "OnDateSelected: " + date.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthChanged(Date date) {
                Toast.makeText(SKPCalendarActivity.this,"OnMonthChanged: " + date.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        HashMap<String, Integer> listNote = new HashMap<>();
        HashMap<String, Integer> listGoal = new HashMap<>();
        listGoal.put("2017-02-12", 0);
        listGoal.put("2017-02-11", -1);
        listGoal.put("2017-02-10", 1);
        listGoal.put("2017-02-09", 1);
        listGoal.put("2017-02-08", -2);
        listGoal.put("2017-02-01", 2);
        listGoal.put("2017-02-14", 1);
        listGoal.put("2017-02-16", null);
        listNote.put("2017-02-12", 0);
        listNote.put("2017-02-11", -1);
        listNote.put("2017-02-10", 1);
        listNote.put("2017-02-09", 1);
        listNote.put("2017-02-08", -2);
        listNote.put("2017-02-01", 2);
        listNote.put("2017-02-14", 1);
        listNote.put("2017-02-16", null);
        calendar.setListDateConfig(listNote, listGoal);
    }
}
