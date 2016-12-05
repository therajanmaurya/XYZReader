package com.opensource.xyz.reader.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.opensource.xyz.reader.injection.component.ApplicationComponent;
import com.opensource.xyz.reader.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
