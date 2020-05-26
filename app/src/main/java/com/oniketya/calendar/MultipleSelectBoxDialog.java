package com.oniketya.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MultipleSelectBoxDialog implements View.OnClickListener {
    TextView textView;
    Context context;
    String dialogTitle;
    String[] items;
    String itemsString;
    boolean[] selectedItems;

    public MultipleSelectBoxDialog(Context context, TextView textView, String[] items, boolean[] selectedItems, String dialogTitle) {
        this.textView = textView;
        this.context = context;
        this.dialogTitle = dialogTitle;
        this.items = items;
        this.selectedItems = selectedItems;
        textView.setOnClickListener(this);
        Log.i("tag", "multiple");
    }

    @Override
    public void onClick(View v) {
        Log.i("tag", "onclick");
        showDialog();
    }


    public void showDialog() {
        Log.i("tag", "showdialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dialogTitle);
        builder.setMultiChoiceItems(items, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedItems[which] = isChecked;

                String currentItem = items[which];
                Toast.makeText(context,"current:" + currentItem, Toast.LENGTH_LONG);

            }
        });
        builder.setPositiveButton("ADD REMINDERS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsString = "[";
                for(int i=0; i<selectedItems.length; i++){
                    boolean isChecked = selectedItems[i];
                    if(isChecked)
                        itemsString += items[i] + ", ";
                }
                itemsString = itemsString.substring(0,itemsString.length() - 2);
                itemsString += "]";
                textView.setText(itemsString);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public String[] getItems() {
        return items;
    }

    public String getItemsString() {
        return itemsString;
    }

    public boolean[] getSelectedItems() {
        return selectedItems;
    }
}
