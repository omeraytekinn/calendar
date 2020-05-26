package com.oniketya.calendar;

import java.util.Calendar;
import java.util.Date;

public class CalendarFunctions {


    // Type transforming operations
    public static String dateFromIntegers(int day, int month, int year){
        month += 1;
        String strDay = day<10? "0"+day:""+day;
        String strMonth = month<10?"0"+month:""+month;
        return ""+strDay+"/"+strMonth+"/"+year;
    }

    public static String getTodayString(){
        return dateFromIntegers(Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR)
        );
    }


}
