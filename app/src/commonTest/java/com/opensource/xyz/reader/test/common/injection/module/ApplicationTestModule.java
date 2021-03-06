package com.opensource.xyz.reader.test.common.injection.module;

import static org.mockito.Mockito.mock;

import android.app.Application;
import android.content.Context;

import com.opensource.xyz.reader.data.DataManager;
import com.opensource.xyz.reader.data.remote.ReaderService;
import com.opensource.xyz.reader.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    ReaderService provideReaderService() {
        return mock(ReaderService.class);
    }

}
