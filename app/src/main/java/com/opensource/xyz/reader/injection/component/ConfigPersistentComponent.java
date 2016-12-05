package com.opensource.xyz.reader.injection.component;

import com.opensource.xyz.reader.injection.ConfigPersistent;
import com.opensource.xyz.reader.injection.module.ActivityModule;

import dagger.Component;

import com.opensource.xyz.reader.ui.base.BaseActivity;

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check {@link BaseActivity} to see how this components
 * survives configuration changes.
 * Use the {@link ConfigPersistent} scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}