package com.test.dagger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ClassB {
    private final String option;

    @Inject
    public ClassB(@Named("option") String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
