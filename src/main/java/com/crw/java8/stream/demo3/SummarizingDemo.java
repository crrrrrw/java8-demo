package com.crw.java8.stream.demo3;

import java.util.Comparator;
import java.util.function.BinaryOperator;

import static java.util.stream.Collectors.*;

public class SummarizingDemo {

    public static void main(String... args) {
        System.out.println("total count : " + Dish.menu.stream().collect(counting()));

        Dish dish1 = Dish.menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
        System.out.println("most caloric dish : " + dish1);
        Dish dish2 = Dish.menu.stream().collect(reducing(BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories)))).get();
        System.out.println("most caloric dish : " + dish2);

        System.out.println("total calories : " + Dish.menu.stream().collect(summingInt(Dish::getCalories)));
        System.out.println("average calories : " + Dish.menu.stream().collect(averagingInt(Dish::getCalories)));
        System.out.println("statistics : " + Dish.menu.stream().collect(summarizingInt(Dish::getCalories)));
        System.out.println("joining menu : " + Dish.menu.stream().map(Dish::getName).collect(joining()));// 字符串拼接
        System.out.println("joining menu with ',': " + Dish.menu.stream().map(Dish::getName).collect(joining(", ")));
    }

}
