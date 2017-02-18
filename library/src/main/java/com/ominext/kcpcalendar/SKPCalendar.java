package com.ominext.kcpcalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ominext.kcpcalendar.adapter.SKPCalendarAdapter;
import com.ominext.kcpcalendar.listener.CalendarSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Vin on 10/23/2016.
 */
public class SKPCalendar extends RelativeLayout {

    RelativeLayout rootView;
    private SKPCalendarAdapter calV = null;
    private GridView gridView;
    private static int jumpMonth = 0;
    private static int jumpYear = 0;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    private HashMap<String, Integer> listNote;
    private HashMap<String, Integer> listGoal;

    private CalendarSelectionListener listener;

    public SKPCalendar(Context context) {
        super(context);
        init();
    }

    public SKPCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SKPCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.skp_calendar_layout, null, false);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        rootView = (RelativeLayout) view.findViewById(R.id.rootView);
        gridView = (GridView) view.findViewById(R.id.gridCalendar);
        calV = new SKPCalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c, listNote, listGoal);
        addGridView();
        gridView.setAdapter(calV);
        addView(view);
    }

    private void addGridView() {
        gridView.setBackgroundColor(Color.parseColor("#bfbfbf"));
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        float h = getResources().getDisplayMetrics().density;
        gridView.setVerticalSpacing((int) (1f * h));
        gridView.setHorizontalSpacing((int) (1f * h));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                //Only touch for date in show Mounth
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];
                    String scheduleYear = calV.getShowYear();
                    String scheduleMonth = calV.getShowMonth();
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.YEAR, Integer.parseInt(scheduleYear));
                    date.set(Calendar.MONTH, Integer.parseInt(scheduleMonth) - 1);
                    date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(scheduleDay));
                    if(listener != null) {
                        listener.onDateSelected(date.getTime());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calV.setSelectedDate(sdf.format(date.getTime()));
                        Log.d("DateSelected", sdf.format(date.getTime()));
                    }
                }
            }
        });
    }

    public void setListDateConfig(HashMap<String, Integer> listNote, HashMap<String, Integer> listGoal) {
        this.listGoal = listGoal;
        this.listNote = listNote;
        showMonth(year_c, month_c);
    }

    public void nextMonth() {
        if (month_c < 12) month_c = month_c + 1;
        else {
            month_c = 1;
            year_c +=1;
        }
        showMonth(year_c, month_c);
    }

    public void prevMonth() {
        if (month_c > 1) month_c = month_c - 1;
        else {
            month_c = 12;
            year_c -=1;
        }
        showMonth(year_c, month_c);
    }

    /**
     * Show mount and show configs
     * @param year
     * @param month
     *
     */
    public void showMonth(int year, int month, HashMap<String, Integer> listNote, HashMap<String, Integer> listGoal){
        this.listGoal = listGoal;
        this.listNote = listNote;
        showMonth(year, month);
    }

    /**
     * Show month with option year and month
     * @param month
     * @param year
     */
    public void showMonth(int year, int month) {
        if (year < 0 || month < 0 || month > 12) return;
        year_c = year;
        month_c = month;
        if(gridView == null) return;
        calV = new SKPCalendarAdapter(getContext(), getResources(), year_c, month_c, day_c, listNote, listGoal);
        gridView.setAdapter(calV);
        if(listener != null) listener.onMonthChanged(getCurrentShowMonthByDate());
    }


    /**
     * Get current month showing
     * @return currentMonth
     */
    public String getCurrentShowMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-Dd");
        return sdf.format(getCurrentShowMonthByDate());
    }

    public Date getCurrentShowMonthByDate(){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year_c);
        date.set(Calendar.MONTH, month_c - 1);
        return date.getTime();
    }

    public void setCalendarSelectionListener(CalendarSelectionListener listener) {
        this.listener = listener;
    }
}
