package com.crw.java8.stream.demo2;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MatchAndFindDemo {

    public static void main(String... args) {
        boolean isVegetarianFriendlyMenu = Dish.menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println("all Vegetarian ? " + isVegetarianFriendlyMenu);

        boolean isHealthyMenu = Dish.menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println("all healthily ? " + isHealthyMenu);

        boolean isHealthyMenu2 = Dish.menu.stream().noneMatch(d -> d.getCalories() >= 1000);
        System.out.println("all healthily ? " + isHealthyMenu2);

        Optional<Dish> dish = Dish.menu.stream().filter(Dish::isVegetarian).findAny();
        System.out.println("is present vegetarian ? " + dish.isPresent());
        dish.ifPresent(d -> System.out.println(d.getName()));

        System.out.println("----------------");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> first = numbers.stream()
                .map(i -> i * i)
                .filter(i -> i % 3 == 0)
                .findFirst();
        System.out.println(first.get());
    }

}
