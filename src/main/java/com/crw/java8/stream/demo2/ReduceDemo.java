package com.crw.java8.stream.demo2;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ReduceDemo {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        int calories = Dish.menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("total calories:" + calories);

        System.out.println("------------开始分析 reduce 方法-----------");

        // 初始值是 1，返回类型是 Optional<T>
        Optional<Integer> reduce1 = Stream.of(1, 2, 3, 4, 5).reduce((a, b) -> {
            System.out.println(a);
            System.out.println(b);
            a += b;
            System.out.println(a);
            System.out.println("~~~~");
            return a;
        });
        System.out.println("result:" + reduce1.get());

        System.out.println("-----------------------");

        // 给定初始值是 0，返回类型是 T
        Integer reduce2 = Stream.of(1, 2, 3, 4, 5).reduce(0, (a, b) -> {
            System.out.println(a);
            System.out.println(b);
            a += b;
            System.out.println(a);
            System.out.println("~~~~");
            return a;
        });
        System.out.println("result:" + reduce2);

        System.out.println("-----------------------");

        // Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果。
        // 这也说明了了第三个函数参数的数据类型必须为返回数据类型了。
        /*ArrayList<Integer> reduce3 = Stream.of(1, 2, 3, 4, 5).reduce(new ArrayList<Integer>(),
                (list, i) -> {
                    System.out.println("BiFunction");
                    System.out.println(list);
                    System.out.println(i);
                    list.add(i);
                    System.out.println(list);
                    System.out.println("~~~~");
                    return list;
                },
                (list1, list2) -> {
                    System.out.println("BinaryOperator");
                    System.out.println(list1);
                    System.out.println(list2);
                    list1.addAll(list2);
                    System.out.println(list1);
                    System.out.println("#####");
                    return list1;
                });

        System.out.println("result:" + reduce3);
        System.out.println(reduce3.size());*/
    }

}
