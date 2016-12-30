package com.opensource.xyz.reader;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.opensource.xyz.reader.injection.component.ApplicationComponent;
import com.opensource.xyz.reader.injection.component.DaggerApplicationComponent;
import com.opensource.xyz.reader.injection.module.ApplicationModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class ReaderApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics());
        }
    }

    public static ReaderApplication get(Context context) {
        return (ReaderApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
