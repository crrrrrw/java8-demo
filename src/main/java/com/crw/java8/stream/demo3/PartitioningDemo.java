package com.crw.java8.stream.demo3;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class PartitioningDemo {

    public static void main(String[] args) {
        Map<Boolean, List<Dish>> partitionByVegeterian =
                Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("按是否为素食分区: " + partitionByVegeterian);

        Map<Boolean, Map<Dish.Type, List<Dish>>> partitionByVegeterianGroupByType =
                Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println("先按是否为素食分区，再按类型分组: " + partitionByVegeterianGroupByType);

        Map<Boolean, Dish> partitionByVegeterianToFindMostCalories =
                Dish.menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get)));
        System.out.println("先按是否为素食分区，再找出每个区最多的卡里路:" + partitionByVegeterianToFindMostCalories);
    }

}

