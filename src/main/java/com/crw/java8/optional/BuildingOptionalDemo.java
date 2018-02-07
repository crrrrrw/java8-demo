package com.crw.java8.optional;

import java.util.NoSuchElementException;
import java.util.Optional;

public class BuildingOptionalDemo {

    public static void main(String[] args) {
        // 声明一个空的Optional
        Optional<Car> optCar = Optional.empty();
        // 是否存在
        System.out.println(optCar.isPresent());
        try {
            System.out.println(optCar.get());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        optCar.ifPresent((o) -> {
            System.out.println("insurance:" + o.getInsurance());
        });
        // 如果 Optional不存在，则赋值给默认值
        Car car1 = optCar.orElse(new Car(Optional.of(new Insurance("default"))));
        System.out.println(car1);

        System.out.println("------------------");

        // 依据一个非空值创建Optional
        Car car = new Car();
        Optional<Car> optCar2 = Optional.of(car);
        System.out.println(optCar2.isPresent());
        try {
            System.out.println(optCar2.get());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        optCar2.ifPresent((o) -> {
            System.out.println("insurance:" + o.getInsurance());
        });
        Car car2 = optCar2.orElse(new Car(Optional.of(new Insurance("default"))));
        System.out.println(car2);

        System.out.println("------------------");

        // 可接受null的Optional
        Optional<Car> optCar3 = Optional.ofNullable(null);
        System.out.println(optCar3.isPresent());
        try {
            System.out.println(optCar3.get());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        optCar3.ifPresent((o) -> {
            System.out.println("insurance:" + o.getInsurance());
        });
        Car car3 = optCar3.orElse(new Car(Optional.of(new Insurance("default"))));
        System.out.println(car3);
    }

}
