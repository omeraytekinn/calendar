package com.oniketya.calendar;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends BaseAdapter {

    List<EventModal> events;
    Context context;

    public EventAdapter(List<EventModal> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.event_list_item,parent,false);

        TextView startTime = convertView.findViewById(R.id.event_item_start_time);
        TextView endTime = convertView.findViewById(R.id.event_item_end_time);

        TextView eventTitle = convertView.findViewById(R.id.event_item_title);

        final EventModal event = events.get(position);

        eventTitle.setText(event.getTitle());
        startTime.setText(event.getStartTimeStr());
        if(event.getEndTimeStr() == null || event.getStartDateStr().equals(event.getEndDateStr()) )
            endTime.setText("-");
        else
            endTime.setText(events.get(position).getEndTimeStr());
/*
        date.setText(events.get(position).getDate());
        event.setText(events.get(position).getName());
*/
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SingleEvent.class);
                intent.putExtra("title",event.getTitle());
                intent.putExtra("details",event.getDetails());
                intent.putExtra("startDate",event.getStartDateStr());
                intent.putExtra("startTime",event.getStartTimeStr());
                intent.putExtra("endDate",event.getEndDateStr());
                intent.putExtra("endTime",event.getEndTimeStr());
                intent.putExtra("repeat",event.getRepeat());
                intent.putExtra("reminder",event.getReminders());
                intent.putExtra("event_id",event.getEvent_id());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
