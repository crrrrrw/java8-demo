package com.crw.java8.stream.demo2;


import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.crw.java8.stream.demo2.Dish.menu;

public class NumericStreamsDemo {

    public static void main(String... args) {

        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("total calories:" + calories);


        // max and OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max = maxCalories.isPresent() ? maxCalories.getAsInt() : 1;
        System.out.println(max);

        // numeric ranges
        IntStream evenNumbers = IntStream.rangeClosed(1, 100) // [1,100]
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        IntStream evenNumbers2 = IntStream.range(1, 100) // [1,100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers2.count());


        /*Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));*/

    }

}
