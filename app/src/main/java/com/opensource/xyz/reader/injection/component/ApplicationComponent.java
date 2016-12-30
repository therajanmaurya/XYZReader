package com.opensource.xyz.reader.injection.component;

import android.app.Application;
import android.content.Context;

import com.opensource.xyz.reader.data.remote.ReaderService;
import com.opensource.xyz.reader.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;
import com.opensource.xyz.reader.data.DataManager;
import com.opensource.xyz.reader.data.SyncService;
import com.opensource.xyz.reader.data.local.DatabaseHelper;
import com.opensource.xyz.reader.data.local.PreferencesHelper;
import com.opensource.xyz.reader.injection.ApplicationContext;
import com.opensource.xyz.reader.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    ReaderService ribotsService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
