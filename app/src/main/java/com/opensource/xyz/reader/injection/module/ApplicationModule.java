package com.opensource.xyz.reader.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.opensource.xyz.reader.data.remote.RibotsService;
import com.opensource.xyz.reader.injection.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    RibotsService provideRibotsService() {
        return RibotsService.Creator.newRibotsService();
    }

}
