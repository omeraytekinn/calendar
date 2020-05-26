package com.oniketya.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeSelector implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    String time;
    int hour, minute;
    Context context;
    EditText timeInput;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String strHour = hourOfDay < 10 ? "0"+hourOfDay:""+hourOfDay;
        String strMinute = minute < 10 ? "0"+minute:""+minute;
        time = strHour + ":" + strMinute;
        this.hour = hourOfDay;
        this.minute = minute;
        timeInput.setText(time);
    }

    @Override
    public void onClick(View v) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        new TimePickerDialog(context,this, hour, minute, true).show();
    }

    public TimeSelector(Context context, EditText timeInput) {
        this.context = context;
        this.timeInput = timeInput;
        this.timeInput.setOnClickListener(this);
    }

    public String getTime() {
        return time;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTimeSet(){
        if (time == null)
            return false;
        else
            return true;
    }
}
