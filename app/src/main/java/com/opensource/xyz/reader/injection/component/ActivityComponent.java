package com.opensource.xyz.reader.injection.component;

import com.opensource.xyz.reader.injection.module.ActivityModule;
import com.opensource.xyz.reader.ui.main.MainActivity;

import dagger.Subcomponent;
import com.opensource.xyz.reader.injection.PerActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
