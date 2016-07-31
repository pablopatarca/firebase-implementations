package com.pablopatarca.firebaseimplementation.app;

import android.app.Application;

/**
 * Created by Pablo on 30/7/16.
 */
public class FirebaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppSettings.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        AppSettings.destroy();
    }
}
