package com.learn.thread;

/**
 * 描述：
 *
 * @author 刘背背[liubeibei@ele-cloud.com]
 * @date 2020/08/05 10:21 PM
 * @since JDK 1.8
 */
public class RunnableThread implements Runnable{

    public static void main(String[] args) {
        RunnableThread rt = new RunnableThread();
        Thread t = new Thread(rt);
        t.start();
    }
    @Override
    public void run(){
        System.out.println("start by runnable……");
    }
}
