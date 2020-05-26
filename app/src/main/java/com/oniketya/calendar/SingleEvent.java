package com.oniketya.calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class SingleEvent extends AppCompatActivity {
    Menu menu;
    TextView titleText,
            startDateText,
            startTimeText,
            endDateText,
            endTimeText,
            locationText,
            detailsText,
            reminderText,
            repeatText;

    // Variables
    String[] reminderItems = {"EventModal Time", "10 Minutes", "30 Minutes", "1 Hour", "1 Day", "1 Week"};
    boolean[] reminderSelected;
    String[] repeaterItems = {"Never", "Daily", "Weekly", "Monthly", "Yearly"};
    int repeaterSelected = 0;
    String  titleString,
            startDateString,
            startTimeString,
            endDateString,
            endTimeString,
            locationString,
            detailsString,
            repeatString,
            remindersString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);
        ActionBar actionBar = getSupportActionBar();

        readExtras();
        getElements();
        fillViews();
    }


    public void readExtras(){
        Bundle extras = getIntent().getExtras();
        titleString = extras.getString("title");
        detailsString = extras.getString("details");
        startDateString = extras.getString("startDate");
        endDateString = extras.getString("endDate");
        startTimeString = extras.getString("startTime");
        endTimeString = extras.getString("endTime");
        repeaterSelected = extras.getInt("repeat");
        reminderSelected = extras.getBooleanArray("reminder");
        Log.i("remind", ""+reminderSelected.length);

        remindersString = "[";
        int count = 0;
        for(int i=0; i<reminderSelected.length; i++){
            boolean isChecked = reminderSelected[i];
            if(isChecked){
                remindersString += reminderItems[i] + " | ";
                count += 1;
            }
        }
        if(count == 0)
            remindersString = "[ None ]";
        else{
            remindersString = remindersString.substring(0,remindersString.length() - 2);
            remindersString += "]";
        }
        repeaterSelected = repeaterSelected<0 ?0:repeaterSelected;
        repeatString = repeaterItems[repeaterSelected];
    }

    public void getElements() {
        titleText = findViewById(R.id.single_event_title);
        startDateText = findViewById(R.id.single_event_start_date);
        startTimeText = findViewById(R.id.single_event_start_time);
        endDateText = findViewById(R.id.single_event_end_date);
        endTimeText = findViewById(R.id.single_event_end_time);
        locationText = findViewById(R.id.single_event_position);
        detailsText = findViewById(R.id.single_event_details);
        reminderText = findViewById(R.id.single_event_reminders);
        repeatText = findViewById(R.id.single_event_repeat);

    }

    public void fillViews(){
        titleText.setText(titleString);

        startDateText.setText(startDateString);
        startTimeText.setText(startTimeString);
        endDateText.setText(endDateString);
        endTimeText.setText(endTimeString);
        locationText.setText(locationString);
        detailsText.setText(detailsString);
        reminderText.setText(remindersString);
        repeatText.setText(repeatString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.single_event_menu, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share_event_menu) {
            Toast toast = Toast.makeText(this, "Share Will Come", Toast.LENGTH_LONG);
            View view = toast.getView();

            //Gets the actual oval background of the Toast then sets the colour filter
            view.getBackground().setColorFilter(Color.argb(220,10,100,50), PorterDuff.Mode.SRC_IN);

            toast.show();
            onBackPressed();
            return true;
        }
        if (id == R.id.home_event_menu) {
            onBackPressed();
            return true;
        }

        if (id == R.id.edit_event_menu) {

            Intent intent = new Intent(this, CreateEvent.class);
            intent.putExtras(getIntent());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }
}
