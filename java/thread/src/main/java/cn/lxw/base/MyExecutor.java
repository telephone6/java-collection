package cn.lxw.base;

import java.util.concurrent.Executors;

public class MyExecutor {

    public static void main(String[] args) {
        Executors.newCachedThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Created by by Executors");
            }
        });
    }
}
