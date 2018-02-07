package com.crw.java8.lambda.demo4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 复合lambda
 */
public class CompositionDemo {

    public static void main(String[] args) {
        // 准备一些苹果
        List<Apple> appleList = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"),
                new Apple(120, "green"));

        // 1.比较器复合
        Comparator<Apple> weightComparator = Comparator.comparing(Apple::getWeight);
        // 逆序排序
        appleList.sort(weightComparator.reversed());
        System.out.println(appleList);
        // Comparator 一些其他组合
        Comparator<Apple> weightColorComparator = weightComparator.thenComparing((a1, a2) -> a1.getColor().compareToIgnoreCase(a2.getColor()));
        appleList.sort(weightColorComparator);
        System.out.println(appleList);

        System.out.println("-----------------------------------------");
        // 2.谓词复合
        Predicate<Apple> p = apple -> "green".equalsIgnoreCase(apple.getColor());
        System.out.println(filterApples(appleList, p));
        System.out.println(filterApples(appleList, p.negate()));
        System.out.println(filterApples(appleList, p.and(apple -> apple.getWeight() > 150)));
        System.out.println(filterApples(appleList, p.or(apple -> apple.getWeight() == 120)));

        System.out.println("-----------------------------------------");
        // 3.函数复合
        Integer x = 5;
        Function<Integer, Integer> f = (i) -> i * 2; // f(x) = x*2
        Function<Integer, Integer> g = (i) -> i * i;// g(x) = x^2
        System.out.println(x);
        System.out.println(f.andThen(g).apply(5)); // g(f(x))
        System.out.println(f.compose(g).apply(5)); // f(g(x))
        System.out.println(Function.identity()); // f(g(x))
    }

    public static List<Apple> filterApples(List<Apple> appleList, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : appleList) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    private static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Apple() {
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }

    }
}
