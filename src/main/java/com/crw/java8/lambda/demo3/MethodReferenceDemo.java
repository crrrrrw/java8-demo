package com.crw.java8.lambda.demo3;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用
 */
public class MethodReferenceDemo {

    public static void main(String[] args) {
        // 1. 静态方法的方法引用
        System.out.println(func1("1", s -> Integer.valueOf(s)));
        System.out.println(func1("1", Integer::valueOf));

        // 2. 指向任意类型实例方法的方法引用
        System.out.println(func2("abcde", 2, (str, integer) -> str.substring(integer)));
        System.out.println(func2("abcde", 2, String::substring));

        // 3. 指向现有对象的实例方法的方法引用
        Apple appleExpr = new Apple(122, "red");
        Apple appleArg = new Apple(105, "green");
        System.out.println(func3(appleArg, (Apple arg) -> appleExpr.compareAppleWight(arg)));
        System.out.println(func3(appleArg, appleExpr::compareAppleWight));


        // eg 一些与lambda等价的方法引用写法
        Function<String, Integer> stringToInteger = (String s) -> Integer.parseInt(s);
        Function<String, Integer> stringToInteger2 = Integer::parseInt;

        BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> contains2 = List::contains;


        // 构造函数的方法引用
        Supplier<Apple> supplier = Apple::new;
        Apple apple1 = supplier.get();
        System.out.println(apple1);

        BiFunction<Integer, String, Apple> biFunction = Apple::new;
        Apple apple2 = biFunction.apply(155, "green");
        System.out.println(apple2);

    }

    public static int func1(String str, Function<String, Integer> func) {
        return func.apply(str);
    }

    public static String func2(String str, int i, BiFunction<String, Integer, String> func) {
        return func.apply(str, i);
    }

    public static Integer func3(Apple apple, Function<Apple, Integer> func) {
        return func.apply(apple);
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

        public int compareAppleWight(Apple apple) {
            return getWeight().compareTo(apple.getWeight());
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }

    }
}
