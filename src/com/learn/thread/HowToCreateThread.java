package com.learn.thread;

import java.util.concurrent.*;

/**
 * 描述：
 *
 * @author lbb-1011
 * @date 2020/08/05 10:21 PM
 * @since JDK 1.8
 */
public class HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("start myThread!!-------------" + i);
            }
        }
    }

    static class MyRunable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("start myRunnale!!-" + i);
            }
        }
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum++;
            }
            return sum;
        }
    }

    static class MyPool implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("myPool is start===" + i);
            }
        }
    }

    public static void main(String[] args) {
        //1.通过继承Thread实现
        new MyThread().start();
        //2.通过继承runnable接口实现
        new Thread(new MyRunable()).start();
        //2.1 通过Lambda的方式
        new Thread(() -> System.out.println("start Lambda Runnable")).start();
        //==========以下为JDK1.5新增的方法=====================
        //3.实现Callable接口
        FutureTask<Integer> ft = new FutureTask<>(new MyCallable());
        new Thread(ft).start();
        try {
            Integer sum = ft.get();
            System.out.println("sum=" + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4.使用线程池
        //4-1 创建线程池，初始化线程数
        ExecutorService pool = Executors.newFixedThreadPool(10);
        //可以自动扩展
        ExecutorService pool2 = Executors.newCachedThreadPool();
        //4-2 开启线程
        pool.execute(new MyPool());
        pool.execute(new MyPool());
        //4-3 Lambda方式
        pool.execute(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("myPool lambda start===" + i);
            }
        });
        //4-4 可以修改一些参数
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) pool;
        threadPoolExecutor.setCorePoolSize(5);
        /**
         * corePoolSize：核心线程池大小
         * maximumPoolSize：最大线程池大小
         * keepAliveTime：线程池最大空闲时间
         */
        threadPoolExecutor.execute(new MyPool());

        //总结：不论是runnable接口的run()方法，还是callable的call()方法，都不允许直接被调用
        //通过Thread的start()方法启动，或者线程池启动
    }

}
