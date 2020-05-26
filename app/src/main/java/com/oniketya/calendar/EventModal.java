package com.oniketya.calendar;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class EventModal extends RealmObject {

    @PrimaryKey
    private int event_id;

    private String title;
    private String startDateStr;
    private String startTimeStr;
    private String endDateStr;
    private String endTimeStr;
    private String locationStr;
    private Float locationX;
    private Float locationY;
    private String details;
    private RealmList<Boolean> reminders;
    private Integer repeat;



    // Getter And Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getLocationStr() {
        return locationStr;
    }

    public void setLocationStr(String locationStr) {
        this.locationStr = locationStr;
    }

    public Float getLocationX() {
        return locationX;
    }

    public void setLocationX(Float locationX) {
        this.locationX = locationX;
    }

    public Float getLocationY() {
        return locationY;
    }

    public void setLocationY(Float locationY) {
        this.locationY = locationY;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean[] getReminders() {
        boolean[] boolReminders = new boolean[reminders.size()];
        for(int i=0; i<reminders.size(); i++){
            boolReminders[i] = reminders.get(i);
        }
        return boolReminders;
    }

    public void setReminders(boolean[] reminders) {
        this.reminders = new RealmList<>();
        for(int i=0; i<reminders.length; i++){
            this.reminders.add(reminders[i]);
        }
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public int getEvent_id() {
        return event_id;
    }
}

