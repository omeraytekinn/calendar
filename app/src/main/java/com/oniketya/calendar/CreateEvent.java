package com.oniketya.calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import io.realm.Realm;

public class CreateEvent extends AppCompatActivity {

    Menu menu;

    // View Elements
    EditText titleInput,
             startDateInput,
             startTimeInput,
             endDateInput,
             endTimeInput,
             locationInput,
             detailsInput;
    TextView remindersInput,
             repeatInput;
    DateSelector startDateSelector,
                 endDateSelector;
    TimeSelector startTimeSelector,
                 endTimeSelector;

    MultipleSelectBoxDialog reminderSelector;
    SingleSelectBoxDialog repeaterSelector;


    // Variables
    Realm realm;
    String[] reminderItems = {"EventTime", "10 Minutes", "30 Minutes", "1 Hour", "1 Day", "1 Week"};
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
    int event_id = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getSupportActionBar().setTitle("Create Event");
        defineAllItems();
        setDefaults();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void defineAllItems(){
        titleInput = findViewById(R.id.title_input);
        startDateInput = findViewById(R.id.start_date_input);
        startTimeInput = findViewById(R.id.start_time_input);
        endDateInput = findViewById(R.id.end_date_input);
        endTimeInput = findViewById(R.id.end_time_input);
        locationInput = findViewById(R.id.location_input);
        detailsInput = findViewById(R.id.details_input);
        remindersInput = findViewById(R.id.reminders_input);
        repeatInput = findViewById(R.id.repeat_input);
    }

    public void setDefaults(){

        boolean isExtra = readExtras();
        startDateSelector = new DateSelector(this, startDateInput);
        endDateSelector = new DateSelector(this, endDateInput);
        startTimeSelector = new TimeSelector(this, startTimeInput);
        endTimeSelector = new TimeSelector(this, endTimeInput);

        reminderSelector = new MultipleSelectBoxDialog(this, remindersInput, reminderItems, reminderSelected, "Event Reminders");
        repeaterSelector = new SingleSelectBoxDialog(this, repeatInput, repeaterItems, repeaterSelected, "Repeat Event");

        realm = Realm.getDefaultInstance();

        // If Edit initialize textedit contents
        if(isExtra){
            titleInput.setText(titleText);
            startDateInput.setText(startDateText);
            startTimeInput.setText(startTimeText);
            endDateInput.setText(endDateText);
            endTimeInput.setText(endTimeText);
            locationInput.setText(locationText);
            detailsInput.setText(detailsText);
            remindersInput.setText(remindersText);
            repeatInput.setText(repeatText);
            startDateSelector.setDate(startDateText);
            endDateSelector.setDate(endDateText);
            startTimeSelector.setTime(startTimeText);
            endTimeSelector.setTime(endTimeText);
        }


    }

    public boolean readExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            titleText = extras.getString("title");
            detailsText = extras.getString("details");
            startDateText = extras.getString("startDate");
            endDateText = extras.getString("endDate");
            startTimeText = extras.getString("startTime");
            endTimeText = extras.getString("endTime");
            repeaterSelected = extras.getInt("repeat");
            reminderSelected = extras.getBooleanArray("reminder");
            event_id = extras.getInt("event_id");

            Log.i("remind", ""+reminderSelected.length);

            remindersText = "[";
            int count = 0;
            for(int i=0; i<reminderSelected.length; i++){
                boolean isChecked = reminderSelected[i];
                if(isChecked){
                    remindersText += reminderItems[i] + " | ";
                    count += 1;
                }
            }
            if(count == 0)
                remindersText = "[ None ]";
            else{
                remindersText = remindersText.substring(0,remindersText.length() - 2);
                remindersText += "]";
            }
            repeaterSelected = repeaterSelected<0 ?0:repeaterSelected;
            repeatText = repeaterItems[repeaterSelected];
            return true;
        } else {
            return false;
        }
    }

    public void readInputValues() {

        startDateText = startDateSelector.getDate();
        startTimeText = startTimeSelector.getTime();
        endDateText = endDateSelector.getDate();
        endTimeText = endTimeSelector.getTime();

        titleText = titleInput.getText().toString().equals("") ? null:titleInput.getText().toString();
        detailsText = detailsInput.getText().toString().equals("") ? null:detailsInput.getText().toString();
        locationText = locationInput.getText().toString().equals("") ? null:locationInput.getText().toString();

        repeaterSelected = repeaterSelector.getSelectedItemPosition();
        reminderSelected = reminderSelector.getSelectedItems();
    }

    public boolean saveEvent(){

        if(!startDateSelector.isDateSet() || !startTimeSelector.isTimeSet() || titleText == null)
            return false;
        else{
            realm.executeTransactionAsync(new Realm.Transaction() {

                EventModal event;
                @Override
                public void execute(Realm bgRealm) {
                    if(event_id == -1) {
                        Number maxID = bgRealm.where(EventModal.class).max("event_id");

                        event_id = maxID == null ? 1:maxID.intValue() + 1;
                        event = bgRealm.createObject(EventModal.class, event_id);
                    } else {
                        event = bgRealm.where(EventModal.class).equalTo("event_id", event_id).findFirst();
                    }


                    event.setTitle(titleText);
                    event.setStartDateStr(startDateText);
                    event.setStartTimeStr(startTimeText);
                    event.setRepeat(repeaterSelected);
                    event.setReminders(reminderSelected);

                    if(endDateSelector.isDateSet())
                        event.setEndDateStr(endDateText);
                    if(endTimeSelector.isTimeSet())
                        event.setEndTimeStr(endTimeText);
                    if(detailsText != null)
                        event.setDetails(detailsText);
                    if(locationText != null)
                        event.setLocationStr(locationText);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Transaction was a success.

                    Toast toast = Toast.makeText(getApplicationContext(), "Event Created Successfully", Toast.LENGTH_LONG);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view.getBackground().setColorFilter(Color.argb(220,10,100,50), PorterDuff.Mode.SRC_IN);
                    toast.show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    // Transaction failed and was automatically canceled.
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view.getBackground().setColorFilter(Color.argb(250,100,20,30), PorterDuff.Mode.SRC_IN);
                    toast.show();
                }
            });
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.event_create_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cancel_create_event) {
            onBackPressed();
            return true;
        }

        if (id == R.id.submit_create_event) {
            readInputValues();
            if(saveEvent()){
                onBackPressed();
                return true;
            } else {

                Toast toast = Toast.makeText(this, "Error: Fill Missing Inputs", Toast.LENGTH_LONG);
                View view = toast.getView();

                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.argb(250,100,20,30), PorterDuff.Mode.SRC_IN);
                toast.show();
                return false;
            }

        }

        return super.onOptionsItemSelected(item);
    }


}
