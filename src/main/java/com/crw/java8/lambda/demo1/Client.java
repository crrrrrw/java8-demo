package com.crw.java8.lambda.demo1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 初识lambda
 */
public class Client {

    public static void main(String[] args) {
        // 准备一些苹果
        List<Apple> appleList = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        // jdk8之前，需求变更，通过添加新的策略修改行为
        List<Apple> greenApples = filterApples(appleList, new AppleGreenColorPredicate());
        System.out.println(greenApples);
        List<Apple> heavyApples = filterApples(appleList, new AppleHeavyWeightPredicate());
        System.out.println(heavyApples);
        // ... 需求变更后需要添加新的策略实现
        // 或者使用匿名内部类的方式
        List<Apple> redApples = filterApples(appleList, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redApples);

        // jdk8 lambda表达式
        List<Apple> lightApples = filterApples(appleList, (Apple apple) -> apple.getWeight() < 150);
        System.out.println(lightApples);

        // 使用 jdk8 提供的 java.util.function.Predicate
        List<Apple> lightAndGreenApples = filterApples2(appleList, (Apple apple) -> apple.getWeight() < 150 && "green".equalsIgnoreCase(apple.getColor()));
        System.out.println(lightAndGreenApples);

        // 其他的lambda例子
        // 列表排序
        appleList.sort((apple1, apple2) -> apple1.getWeight().compareTo(apple2.getWeight()));
        System.out.println(appleList);

        // Runnable
        Thread t = new Thread(() -> {
            System.out.println("abc");
        });
        t.start();

    }

    // 根据自定义苹果挑选策略筛选苹果
    public static List<Apple> filterApples(List<Apple> appleList, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 根据jdk8提供的Predicate接口筛选苹果
    public static List<Apple> filterApples2(List<Apple> appleList, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}
