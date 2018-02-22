package com.crw.java8.asyn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步调用过程中发生异常
 */
public class CompletableFutureExceptionDemo {

    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(() -> {
            try {
                //模拟执行耗时任务
                System.out.println("task doing...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("抛异常了");
            } catch (Exception e) {
                //告诉completableFuture任务发生异常了
                completableFuture.completeExceptionally(e);
            }
        }).start();
        doSomethingElse();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = null;
        try {
            result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("计算结果:" + result);
    }

    private static void doSomethingElse() {
        System.out.println("do something else...");
    }
}
