package com.crw.java8.lambda.demo2;

/**
 * 函数式接口
 */
public class LambdaDemo {

    public static void main(String[] args) {
        LambdaInterface l1 = () -> "hello world";
        test(l1);

        LambdaInterface2 l2 = (s1, s2) -> s1 + s2;
        test2(l2);

        LambdaInterface3 l3 = (a, b) -> System.out.println(a + b);
        l3.test3(13, 12);

        LambdaInterface4 l4 = (String... strs) -> {
            int sum = 0;
            for (String str : strs) {
                System.out.println(str);
                sum += str.length();
            }
            return sum;
        };
        System.out.println(l4.test4("hello", "world4", "abc", "def"));
    }

    public static void test(LambdaInterface l) {
        System.out.println(l.test1());
        l.defaultMethod();
        LambdaInterface.staticMethod();
    }

    public static void test2(LambdaInterface2 l) {
        System.out.println(l.test2("hello", " world2"));
    }

}
