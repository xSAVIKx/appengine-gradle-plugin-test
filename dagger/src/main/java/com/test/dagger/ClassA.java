package com.test.dagger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClassA {
    private final ClassB instance;

    @Inject
    public ClassA(ClassB instance) {
        this.instance = instance;
    }

    public void print() {
        System.out.println(instance.getOption());
    }
}
