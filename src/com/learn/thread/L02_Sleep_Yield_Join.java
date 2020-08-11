package com.learn.thread;

import java.util.concurrent.locks.LockSupport;

public class L02_Sleep_Yield_Join {
    public static void main(String[] args) {
//        testSleep();
//        testYield();
        testJoin();
    }

    static void testSleep() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("testSleep---" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static void testYield() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A is running……" + i);
                if (i % 5 == 0) {
                    //A 线程只要能被5整除，让出CPU进入就绪状态
                    Thread.yield();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("……B is running……" + i);
                if (i % 3 == 0) {
                    //B 线程只要能被3整除，让出CPU进入就绪状态
                    Thread.yield();
                }
            }
        }).start();
        //现象：A逢 5 下一条必执行B线程，B逢3下一条必执行A线程
    }

    static void testJoin() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 is running……" + i);
            }
        });
        Thread t2 = new Thread(() -> {
            System.out.println("t2 step1 ………………");
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 step2 ………………");
        });
        t1.start();
        t2.start();
        // 将 t1 和 t2 合并，变成只有一个线程，按顺序执行
        // t2 在执行完step1之后一定会等t1线程执行完才会执行 step2 ,t1可以自己先执行完
    }
}
