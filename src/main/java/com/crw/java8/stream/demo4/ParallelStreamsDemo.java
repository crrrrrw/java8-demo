package com.crw.java8.stream.demo4;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreamsDemo {

    public static void main(String[] args) {

        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");

        long num = 10000000l;

        long start = System.nanoTime();
        System.out.println(sumOfJava7(num));
        System.out.println("java7 sum time:" + (System.nanoTime() - start));

        start = System.nanoTime();
        System.out.println(sumOfNonParallel(num));
        System.out.println("java8 non-parallel sum time:" + (System.nanoTime() - start));

        start = System.nanoTime();
        System.out.println(sumOfParallel(num));
        System.out.println("java8 parallel sum time:" + (System.nanoTime() - start));// 时间长是因为有装箱拆箱的开销

        start = System.nanoTime();
        System.out.println(rangedSum(num));
        System.out.println("java8 LongStream non-parallel sum time:" + (System.nanoTime() - start));

        start = System.nanoTime();
        System.out.println(parallelRangedSum(num));
        System.out.println("java8 LongStream parallel sum time:" + (System.nanoTime() - start));

        start = System.nanoTime();
        System.out.println("顺序执行结果：" + accumulatorSum(num));
        System.out.println("java7 my accumulator sum time:" + (System.nanoTime() - start));

        start = System.nanoTime();
        System.out.println("并行执行会产生一个错误的结果：" + accumulatorParallelSum(num));
        System.out.println("java8 my accumulator sum time:" + (System.nanoTime() - start));
    }

    // java7的sum计算
    public static long sumOfJava7(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    // java8不使用并行流
    public static long sumOfNonParallel(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    // java8使用并行流
    public static long sumOfParallel(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }

    /**
     * LongStream.rangeClosed直接产生原始类型的long数字，没有装箱拆箱的开销。
     * LongStream.rangeClosed会生成数字范围，很容易拆分为独立的小块。
     */
    // 非并行生成数
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    // 并行生成数
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }


    // 使用自定义累加器累加计算
    public static long accumulatorSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    // 使用自定义累加器并行累加计算，不适合并行执行，有数据竞争
    public static long accumulatorParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }
}
