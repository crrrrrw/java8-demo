package com.crw.java8.optional;

import java.util.Optional;

public class Person {

    private Optional<Car> car;

    public Person() {
    }

    public Person(Optional<Car> car) {
        this.car = car;
    }

    public Optional<Car> getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Person{" +
                "car=" + car +
                '}';
    }
}
