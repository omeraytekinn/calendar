package com.oniketya.calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ViewEvents extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    RelativeLayout scrollView;
    CalendarView calendarView;
    List<EventModal> events;
    EventAdapter adapter;
    ListView listView;
    FloatingActionButton createEventButton;
    Menu menu;


    // Variables
    Realm realm;
    String[] reminderItems = {"EventModal Time", "10 Minutes", "30 Minutes", "1 Hour", "1 Day", "1 Week"};
    boolean[] reminderSelected = {false, false, false, false, false, false};
    String[] repeaterItems = {"Never", "Daily", "Weekly", "Monthly", "Yearly"};
    int repeaterSelected = 0;
    String  titleText,
            startDateText,
            startTimeText,
            endDateText,
            endTimeText,
            locationText,
            detailsText,
            repeatText,
            remindersText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        getElements();
        initElements();
    }

    public void getElements(){
        scrollView = findViewById(R.id.scrollView);
        calendarView = findViewById(R.id.calendarView);
        createEventButton = findViewById(R.id.create_event_button);
    }

    public void initElements() {
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(this);

        realm = Realm.getDefaultInstance();
        String today = CalendarFunctions.getTodayString();
        getEventFromDB(today);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        });
        this.menu = menu;
        return true;
    }

    public void connectListView(){
        listView = findViewById(R.id.eventsMain);
        listView.setEmptyView(findViewById(R.id.empty_event));
        adapter = new EventAdapter(events,this);
        listView.setAdapter(adapter);
    }

    public void getEventFromDB(String date){
        events = new ArrayList<>();

        // Build the query looking at all users:
        RealmQuery<EventModal> query = realm.where(EventModal.class);
        String today = CalendarFunctions.getTodayString();
        query.equalTo("startDateStr", date);

        RealmResults<EventModal> result = query.findAll();

        for(EventModal e: result){
            events.add(e);
        }
        connectListView();
    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        String selectedDate = CalendarFunctions.dateFromIntegers(dayOfMonth,month,year);
        getEventFromDB(selectedDate);
    }
}
