package com.crw.java8.stream.demo2;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FilteringDemo {

    public static void main(String... args) {

        // FilteringDemo with predicate
        List<Dish> vegetarianMenu =
                Dish.menu.stream()
                        .filter(Dish::isVegetarian)
                        .collect(toList());

        vegetarianMenu.forEach(System.out::println);

        System.out.println("------------");
        // FilteringDemo unique elements
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        System.out.println("------------");
        // Truncating a stream
        List<Dish> dishesLimit3 =
                Dish.menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .limit(3)
                        .collect(toList());

        dishesLimit3.forEach(System.out::println);

        System.out.println("------------");
        // Skipping elements
        List<Dish> dishesSkip2 =
                Dish.menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .skip(2)
                        .collect(toList());

        dishesSkip2.forEach(System.out::println);
    }
}
