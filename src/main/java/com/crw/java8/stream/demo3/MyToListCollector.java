package com.crw.java8.stream.demo3;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class MyToListCollector<T> implements Collector<T, List<T>, List<T>> {

    /**
     * 创建一个 Collector
     *
     * @return
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /**
     * 把对象添加到累加器
     *
     * @return
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /**
     * 把A转化为R
     *
     * @return
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    /**
     * 对流的各个子部分进行并行处理时，各个子部分归约所得的累加器要如何合并
     *
     * @return
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * 收集器的行为
     *
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }

    // 测试自定义Collector
    public static void main(String[] args) {
        List<Dish> result = Dish.menu.stream().collect(new MyToListCollector<>());
        System.out.println(result);
    }
}
