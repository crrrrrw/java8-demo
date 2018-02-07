package com.crw.java8.optional;

public class Insurance {

    private String name;

    public String getName() {
        return name;
    }

    public Insurance() {
    }

    public Insurance(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "name='" + name + '\'' +
                '}';
    }
}
