package cn.lxw.base;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    @Override
    public Object call() {
        System.out.println("Created by implements Callable");
        return null;
    }
}
