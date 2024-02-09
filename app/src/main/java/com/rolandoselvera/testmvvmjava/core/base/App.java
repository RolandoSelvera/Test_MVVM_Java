package com.rolandoselvera.testmvvmjava.core.base;

import android.app.Application;

import com.rolandoselvera.testmvvmjava.data.local.db.DatabaseHelper;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized App getInstance() {
        return instance;
    }

    public DatabaseHelper getDatabaseHelper() {
        return new DatabaseHelper(this);
    }
}

