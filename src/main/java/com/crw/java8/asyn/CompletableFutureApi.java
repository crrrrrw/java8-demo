package com.crw.java8.asyn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture中的api方法
 */
public class CompletableFutureApi {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 1. 准备两个任务
         */
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task1 finish...");
            //返回结果
            return "result1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task2 finish...");
            //返回结果
            return "result2";
        });


        // 2. anyOf
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(completableFuture1, completableFuture2);
        System.out.println("率先执行完成的结果是:" + anyOf.get());

        // 3. allOf
        // 对 allOf 方法返回的CompletableFuture 执行 join 操作可以等待CompletableFuture执行完成。
        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFuture1, completableFuture2);
        //阻塞等待所有任务执行完成
        allOf.join();
        System.out.println("所有任务执行完成");

        /**
         * 4. thenCompose
         * 等第一个任务完成后，将任务结果传给参数result，执行后面的任务并返回一个代表任务的completableFuture
         */
        CompletableFuture<String> completableFuture3 = completableFuture1.thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task3 doing...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task3 finish...");
            //返回结果
            return "result3";
        }));
        System.out.println("task3结果:" + completableFuture3.get());

        /**
         * 5. thenCombine
         * 定义了当两个 CompletableFuture 对象完成计算后，结果如何合并。
         */
        CompletableFuture<String> completableFuture4 = completableFuture1.thenCombine(
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    System.out.println("task4 doing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("task4 finish...");
                    //返回结果
                    return "result4";
                }),
                //合并函数
                (result1, result2) -> result1 + result2);

        System.out.println(completableFuture4.get());
    }
}
