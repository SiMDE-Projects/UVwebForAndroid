package fr.utc.assos.uvweb;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class UvwebApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
