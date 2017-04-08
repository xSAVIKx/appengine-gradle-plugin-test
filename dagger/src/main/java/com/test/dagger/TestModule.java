package com.test.dagger;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Module
public class TestModule {
    @Singleton
    @Named("option")
    @Provides
    public String option() {
        return "value";
    }
}
