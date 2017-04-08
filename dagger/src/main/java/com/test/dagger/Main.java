package com.test.dagger;

/**
 * Created by User on 08.04.2017.
 */
public class Main {
    public static void main(String[] args) {
        TestComponent testComponent = DaggerTestComponent.createInstance();
        ClassA classA = testComponent.classA();
        classA.print();
    }
}
