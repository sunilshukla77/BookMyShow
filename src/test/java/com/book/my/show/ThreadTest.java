package com.book.my.show;

public class ThreadTest{
    public static void main(String[] args) throws InterruptedException {
        SeqRun sr= new SeqRun();
        Thread t1 = new Thread(sr);
        Thread t2 = new Thread(sr);
        Thread t3 = new Thread(sr);

        t1.start();
        t2.start();
        t3.start();
        for (int i = 0; i<10; i++) {
            t1.join();
            t2.join();
            t3.join();
        }
    }
}
class SeqRun implements Runnable{
    @Override
    public void run() {
        System.out.println(java.lang.Thread.currentThread().getName());
    }
}
