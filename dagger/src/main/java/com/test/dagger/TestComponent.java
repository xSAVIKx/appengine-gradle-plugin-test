package com.test.dagger;

import dagger.Component;

@Component(modules = {
        TestModule.class
})
public interface TestComponent {
    ClassA classA();
}
