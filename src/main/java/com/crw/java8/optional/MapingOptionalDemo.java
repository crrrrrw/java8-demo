package com.crw.java8.optional;

import java.util.Optional;

public class MapingOptionalDemo {

    public static void main(String[] args) {
        Optional<Insurance> insuranceOpt = Optional.ofNullable(new Insurance("平安保险"));
        Optional<String> nameOpt = insuranceOpt.map(Insurance::getName);
        System.out.println(nameOpt.get());

        Optional<Person> personOpt = Optional.of(new Person(Optional.of(new Car(Optional.of(new Insurance("车险"))))));
        // String name = personOpt.map(Person::getCar).map(Car::getInsurance).map(Insurance::getName);
        String name = personOpt.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknow");
        System.out.println(name);

        Optional<Person> personOpt2 = Optional.ofNullable(new Person(Optional.empty()));
        // 调用链上任意一结果返回空都会返回你期待的值
        String name2 = personOpt2.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknow");
        System.out.println(name2);
    }
}
