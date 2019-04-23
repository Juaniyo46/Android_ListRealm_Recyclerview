package com.example.ejerciciolistas;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;

public class MiAplicacion extends Application {

    public static AtomicInteger sitioId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        setUpRealConfig();
        Realm realm = Realm.getDefaultInstance();
    }
}
