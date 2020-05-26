package com.oniketya.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class SingleSelectBoxDialog implements View.OnClickListener {
    TextView textView;
    Context context;
    String dialogTitle;
    String[] items;
    String itemsString;
    int selectedItemPosition;

    public SingleSelectBoxDialog(Context context, TextView textView, String[] items, int selectedItemPosition, String dialogTitle) {
        this.textView = textView;
        this.context = context;
        this.dialogTitle = dialogTitle;
        this.items = items;
        this.selectedItemPosition = selectedItemPosition;
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showDialog();
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dialogTitle);
        builder.setSingleChoiceItems(items, selectedItemPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItemPosition = which;
            }
        });

        builder.setPositiveButton("SET REPEATER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemsString = items[selectedItemPosition];
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

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }
}
