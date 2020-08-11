package com.learn.thread;

public class L04_SynchronizedDemo implements Runnable {
    public static void main(String[] args) {
        L04_SynchronizedDemo demo = new L04_SynchronizedDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(demo, "Thread" + i).start();
        }

    }

    int count = 10;

    @Override
    public void run() {
//        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + "【count】" + count);
//        }
    }
}
