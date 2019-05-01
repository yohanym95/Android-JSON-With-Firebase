package com.example.yohan.readhub1;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class ReadHub1 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
