package com.learn.thread;

/**
 * 描述：
 *
 * @author 刘背背[liubeibei@ele-cloud.com]
 * @date 2020/08/10 10:46 PM
 * @since JDK 1.8
 */
public class L03_ThreadState {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
                System.out.println("当前线程状态：" + t1.getState());
                System.out.println("等待T1执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("当前线程状态：" + t1.getState());
        t1.start();
        System.out.println("当前线程状态：" + t1.getState());
        t2.start();
        System.out.println("当前线程状态：" + t2.getState());
        System.out.println("当前线程状态：" + t1.getState());


    }
}
