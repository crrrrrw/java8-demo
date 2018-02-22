package com.crw.java8.asyn;

import java.util.concurrent.*;

/**
 * java7的future接口
 * <p>
 * 局限性：
 * 1. 将两个异步计算合并为一个——这两个异步计算之间相互独立，同时第二个又依赖于第
 * 一个的结果。
 * 2. 等待 Future 集合中的所有任务都完成。
 * 3. 仅等待 Future 集合中最快结束的任务完成（有可能因为它们试图通过不同的方式计算同
 * 一个值），并返回它的结果。
 * 4. 通过编程方式完成一个 Future 任务的执行（即以手工设定异步操作结果的方式）。
 * 5. 应对 Future 的完成事件（即当 Future 的完成事件发生时会收到通知，并能使用 Future
 * 计算的结果进行下一步的操作，不只是简单地阻塞等待操作的结果）
 */
public class FutureDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation();// 新线程执行耗时操作
            }

        });
        doSomethingElse(); // 异步操作同时处理其他事情

        try {
            // 获取异步操作的结果，如果最终被阻塞，无法得到结果，那么在最多等待1秒钟之后退出
            Double result = future.get(4, TimeUnit.SECONDS);
            System.out.println("异步操作结果:" + result);
        } catch (ExecutionException ee) {
            // 计算抛出一个异常
            ee.printStackTrace();
        } catch (InterruptedException ie) {
            // 当前线程在等待过程中被中断
            ie.printStackTrace();
        } catch (TimeoutException te) {
            // 在Future对象完成之前超过已过期
            te.printStackTrace();
        }
    }

    private static void doSomethingElse() {
        System.out.println("do something else...");
    }

    private static Double doSomeLongComputation() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1d;
    }
}
