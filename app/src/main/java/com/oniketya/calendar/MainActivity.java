package com.oniketya.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRealm();
        yukle();
    }

    public void yukle() {
        Intent intent = new Intent(this, ViewEvents.class);
        startActivity(intent);
    }

    public void initRealm(){
        realm = Realm.getDefaultInstance();
    }
}
