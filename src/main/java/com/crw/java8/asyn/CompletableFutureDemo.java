package com.crw.java8.asyn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * java8的CompletableFuture接口
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(() -> {
            //模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //告诉completableFuture任务已经完成
            completableFuture.complete("result");
        }).start();

        doSomethingElse();

        try {
            String result = completableFuture.get();
            System.out.println("计算结果:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static void doSomethingElse() {
        System.out.println("do something else...");
    }
}
