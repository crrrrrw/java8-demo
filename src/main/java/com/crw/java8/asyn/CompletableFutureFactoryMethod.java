package com.crw.java8.asyn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 使用工厂方法创建CompletableFuture
 */
public class CompletableFutureFactoryMethod {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //supplyAsync内部使用ForkJoinPool线程池执行任务
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result";
        });

        doSomethingElse();

        System.out.println("计算结果:" + completableFuture.get());

    }

    private static void doSomethingElse() {
        System.out.println("do something else...");
    }
}
