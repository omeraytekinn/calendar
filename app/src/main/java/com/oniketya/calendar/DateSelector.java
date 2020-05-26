package com.oniketya.calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateSelector implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    String date;
    int day, month, year;
    Context context;
    EditText dateInput;

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String strMonth = month<10 ? "0"+month:""+month;
        String strDay = dayOfMonth<10 ? "0"+dayOfMonth:""+dayOfMonth;
        date = ""+strDay+"/"+strMonth+"/"+year;
        this.day = dayOfMonth;
        this.month = month;
        this.year = year;
        dateInput.setText(date);
    }


    public DateSelector(Context context, EditText dateInput) {
        this.context = context;
        this.dateInput = dateInput;
        this.dateInput.setOnClickListener(this);
    }


    public String getDate() {
        return date;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    @Override
    public void onClick(View v) {
        showDatePickerDialog();

    }

    public boolean isDateSet(){
        if (date == null)
            return false;
        else
            return true;
    }
}
