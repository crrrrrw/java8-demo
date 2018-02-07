package com.crw.java8.stream.demo3;

import java.util.*;

import static java.util.stream.Collectors.*;

public class GroupingDemo {

    enum CaloricLevel {DIET, NORMAL, FAT}

    public static void main(String... args) {
        Map<Dish.Type, List<Dish>> groupByType = Dish.menu.stream().collect(groupingBy(Dish::getType));
        System.out.println("按类分组: " + groupByType);

        Map<Dish.Type, List<String>> groupNameByType = Dish.menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println("按类分组，并显示每组成员的名字: " + groupNameByType);


        Map<CaloricLevel, List<Dish>> groupByCalories = Dish.menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println("一级分组：按卡里路等级分组: " + groupByCalories);

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupByTypeAndCalories = Dish.menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }))
        );
        System.out.println("二级分组：先按类分组，再按卡里路等级分组: " + groupByTypeAndCalories);

        Map<Dish.Type, Long> groupByTypeToCount = Dish.menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println("按类分组，每组数量: " + groupByTypeToCount);

        Map<Dish.Type, Optional<Dish>> groupByTypeToFindMostCalories = Dish.menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
        System.out.println("按类分组，找出最多卡里路的食物，返回 Optional 类型: " + groupByTypeToFindMostCalories);

        Map<Dish.Type, Dish> groupByTypeToFindMostCalories3 = Dish.menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));
        System.out.println("按类分组，找出最多卡里路的食物，返回 Dish 类型: " + groupByTypeToFindMostCalories3);


        Map<Dish.Type, Dish> groupByTypeToFindMostCalories2 = Dish.menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(
                                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                                Optional::get)));
        System.out.println("按类分组，找出最多卡里路的食物，返回 Dish 类型: " + groupByTypeToFindMostCalories2);

        Map<Dish.Type, Integer> groupByTypeToSumCalories = Dish.menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println("按类分组，返回每个类型卡里路总数: " + groupByTypeToSumCalories);

        Map<Dish.Type, Set<CaloricLevel>> groupByTypeToCaloriesLevel = Dish.menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        },
                        toSet())));
        System.out.println("按类分组，返回每个类型卡里路的等级: " + groupByTypeToCaloriesLevel);
    }

}
