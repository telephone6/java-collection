package cn.lxw.base;

public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Created by extends Thread");
    }
}
