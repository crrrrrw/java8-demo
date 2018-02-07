package com.crw.java8.lambda.demo2;

public interface LambdaInterface {

    String test1();

    // static修饰符定义静态方法
    static void staticMethod() {
        System.out.println("interface static method");
    }

    // default修饰符定义默认方法
    // 默认方法与抽象方法不同之处在于抽象方法必须要求实现
    default void defaultMethod() {
        System.out.println("interface default method");
    }
}
