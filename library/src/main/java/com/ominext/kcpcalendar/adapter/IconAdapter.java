package com.ominext.kcpcalendar.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ominext.kcpcalendar.R;
import com.ominext.kcpcalendar.models.DateConfig;
import com.ominext.kcpcalendar.models.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class IconAdapter extends BaseAdapter {
    private boolean isLeapyear = false;
    private int daysOfMonth = 0;
    private int dayOfWeek = 0;
    private int lastDaysOfMonth = 0;
    private Context context;
    private String[] dayNumber = new String[42];
    private SpecialCalendar sc = null;
    private Resources res = null;
    private Drawable drawable = null;

    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private int currentFlag = -1;

    private String showYear = ""; // Current year showing
    private String showMonth = ""; // Current month showing
    private String animalsYear = "";
    private String leapMonth = ""; //
    private String cyclical = ""; //

    // System date data
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    //Data date config
    private HashMap<String, DateConfig> listDateConfig;

    public IconAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date);
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];

    }

    public IconAdapter(Context context, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c, HashMap<String, DateConfig> listDateConfig) {
        this();
        this.listDateConfig = listDateConfig;
        this.context = context;
        sc = new SpecialCalendar();
        this.res = rs;

        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }
        currentYear = String.valueOf(stepYear);
        currentMonth = String.valueOf(stepMonth);
        currentDay = String.valueOf(day_c);

        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));

    }

    public IconAdapter(Context context, Resources rs, int year, int month, int day, HashMap<String, DateConfig> dataConfig) {
        this();
        this.listDateConfig = dataConfig;
        this.context = context;
        sc = new SpecialCalendar();
        this.res = rs;
        currentYear = String.valueOf(year);
        currentMonth = String.valueOf(month);
        currentDay = String.valueOf(day);
        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item_icon, null);
        }
        ImageView icNoteIv = (ImageView) convertView.findViewById(R.id.iconIv);
        DateConfig config = null;
        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            if (listDateConfig == null) config = null;
            else {
                String dateStr = dayNumber[position].split("\\.")[2];
                if (dateStr != null) {
                    config = listDateConfig.get(dateStr);
                }
            }
        }
        if(config == null || !config.isHasNote()){
            icNoteIv.setVisibility(View.INVISIBLE);
        }else icNoteIv.setVisibility(View.VISIBLE);
        return convertView;
    }

    //Get data calendar
    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);
        dayOfWeek = sc.getWeekdayOfMonth(year, month);
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
        Log.d("DAY", isLeapyear + " ======  " + daysOfMonth + "  ============  " + dayOfWeek + "  =========   " + lastDaysOfMonth);
        getweek(year, month);
    }

    //Get week data
    private void getweek(int year, int month) {
        int j = 1;
        int flag = 0;
        String lunarDay = "DD";
        for (int i = 0; i < dayNumber.length; i++) {
            if (i < dayOfWeek) {
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                dayNumber[i] = (temp + i) + "." + lunarDay;

            } else if (i < daysOfMonth + dayOfWeek) {
                String day = String.valueOf(i - dayOfWeek + 1);
                dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
                if (sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)) {
                    currentFlag = i;
                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
            } else {
                dayNumber[i] = j + "." + lunarDay;
                j++;
            }
            String numberdate = dayNumber[i].split("\\.")[0];
            if(numberdate.length() == 1) numberdate = "0" + numberdate;
            String monthStr = month +"";
            if(month < 10) monthStr = "0" + month;
            dayNumber[i] = dayNumber[i] + "." + year+"-"+monthStr+"-"+numberdate;
        }

        String abc = "";
        for (int i = 0; i < dayNumber.length; i++) {
            abc = abc + dayNumber[i] + ":";
        }
        Log.d("DAYNUMBER", abc);

    }

    public void matchScheduleDate(int year, int month, int day) {

    }

    /**
     *
     *
     * @param position
     * @return
     */
    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }

    /**
     *
     *
     * @return
     */
    public int getStartPositon() {
        return dayOfWeek + 7;
    }

    /**
     *
     *
     * @return
     */
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

    public String getCyclical() {
        return cyclical;
    }

    public void setCyclical(String cyclical) {
        this.cyclical = cyclical;
    }
}
