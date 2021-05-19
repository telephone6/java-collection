package cn.lxw;

import java.io.File;
import java.time.LocalDateTime;

public class TestProcess {

    public static void main(String[] args) throws InterruptedException {
        // just printing "Hello" every 5 seconds
        while (true) {
            Thread.sleep(5000);
            new Thread(new SayingHelloRunnable()).start();
        }
    }

    static class SayingHelloRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(sayHello("Say hello"));
        }

        public String sayHello(String info){
            return info + " at " + LocalDateTime.now().toString();
        }
    }
}
