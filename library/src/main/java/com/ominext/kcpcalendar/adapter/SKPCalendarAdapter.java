package com.ominext.kcpcalendar.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ominext.kcpcalendar.R;
import com.ominext.kcpcalendar.models.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SKPCalendarAdapter extends BaseAdapter {
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

    private String showYear = "";
    private String showMonth = "";
    private String animalsYear = "";
    private String leapMonth = "";
    private String cyclical = "";
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        notifyDataSetChanged();
    }

    private String selectedDate = "";

    private HashMap<String, Integer> listNote;
    private HashMap<String, Integer> listGoal;

    public SKPCalendarAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date); // 当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];

    }

    public SKPCalendarAdapter(Context context, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c, HashMap<String, Integer> listNote, HashMap<String, Integer> listGoal) {
        this();
        this.listGoal = listGoal;
        this.listNote = listNote;
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

    public SKPCalendarAdapter(Context context, Resources rs, int year, int month, int day, HashMap<String, Integer> listNote, HashMap<String, Integer> listGoal) {
        this();
        this.listNote = listNote;
        this.listGoal = listGoal;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
        ImageView icConner = (ImageView) convertView.findViewById(R.id.icConnerIv);
        ImageView icNotifi = (ImageView) convertView.findViewById(R.id.icNotifiIv);
        String d = dayNumber[position].split("\\.")[0];
        String dv = dayNumber[position].split("\\.")[1];
        Integer noteStatus = null;
        Integer goalStatus = null;
//		SpannableString sp = new SpannableString(d + "\n" + dv);
        SpannableString sp = new SpannableString(d);
        sp.setSpan(new StyleSpan(Typeface.BOLD), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan(1.4f), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (sysDate.equals(dayNumber[position].split("\\.")[2])) {
            //Background and text color
            textView.setBackgroundColor(Color.parseColor("#e4ff82"));
            textView.setTextColor(Color.WHITE);
        } else {
            textView.setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.WHITE);
        }
        textView.setText(sp);
        if (listGoal == null) goalStatus = null;
        else {
            String dateStr = dayNumber[position].split("\\.")[2];
            if (dateStr != null) {
                goalStatus = listGoal.get(dateStr);
            }
        }
        if (listNote == null) noteStatus = null;
        else {
            String dateStr = dayNumber[position].split("\\.")[2];
            if (dateStr != null) {
                noteStatus = listNote.get(dateStr);
            }
        }
        textView.setTextColor(Color.parseColor("#646464"));
        if (position % 7 == 0 || position % 7 == 6) {
            if (position % 7 == 6)
                textView.setTextColor(Color.parseColor("#50b3e5"));// SATURDAT text color
            else
                textView.setTextColor(Color.parseColor("#ff7348"));// SUNDAY text color
        }
        if (!TextUtils.isEmpty(selectedDate) && dayNumber[position].split("\\.")[2].equals(selectedDate)) {
            if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
                textView.setBackgroundColor(Color.parseColor("#ffe9ad"));
//				textView.setTextColor(Color.WHITE);
            }
        }
//		}
        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                textView.setAlpha(1);
            }
            if (goalStatus == null) {
                icConner.setVisibility(View.GONE);
            } else{
                icConner.setVisibility(View.VISIBLE);
                if (goalStatus == 1) {
                    icConner.setImageResource(R.drawable.ic_eclipse_blue);
                } else if (goalStatus == -1) {
                    icConner.setImageResource(R.drawable.ic_close_red);
                } else if (goalStatus == 0) {
                    icConner.setImageResource(R.drawable.ic_triangle_yellow);
                }else if(goalStatus == -2){
                    icConner.setImageResource(R.drawable.ic_cross_red);
                }else{
                    icConner.setVisibility(View.GONE);
                }
            }

            if (noteStatus != null && noteStatus == 1) {
                icNotifi.setVisibility(View.VISIBLE);
            } else icNotifi.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                textView.setAlpha(0.5f);
            }
            icConner.setVisibility(View.GONE);
            icNotifi.setVisibility(View.GONE);
            textView.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }

        return convertView;
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);
        dayOfWeek = sc.getWeekdayOfMonth(year, month);
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
        Log.d("DAY", isLeapyear + " ======  " + daysOfMonth + "  ============  " + dayOfWeek + "  =========   " + lastDaysOfMonth);
        getweek(year, month);
    }

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
            if (numberdate.length() == 1) numberdate = "0" + numberdate;
            String monthStr = month + "";
            if (month < 10) monthStr = "0" + month;
            dayNumber[i] = dayNumber[i] + "." + year + "-" + monthStr + "-" + numberdate;
        }

        String abc = "";
        for (int i = 0; i < dayNumber.length; i++) {
            abc = abc + dayNumber[i] + ":";
        }
        Log.d("DAYNUMBER", abc);

    }

    public void matchScheduleDate(int year, int month, int day) {

    }

    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }

    public int getStartPositon() {
        return dayOfWeek + 7;
    }

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
