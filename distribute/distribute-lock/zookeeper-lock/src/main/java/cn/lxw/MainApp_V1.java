package cn.lxw;

import cn.lxw.service.ILockService;
import cn.lxw.util.LockInfoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MainApp_V1 {

    public static void main(String[] args) throws Exception {
        // run the springboot app
        ConfigurableApplicationContext context = SpringApplication.run(MainApp_V1.class, args);
        // define some lock infomation
        final String lockKey = "lock_test";
        ILockService lockService = (ILockService)context.getBean("zkLock_V1");
        // create a ThreadPoolExecutor
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10));
        // execute the simulator
        for (int i = 0; i < 3; i++) {
            tpe.execute(() -> {
                while (true) {
                    // get the unique lock value of current thread
                    String lockValue = LockInfoUtil.createLockValue();
                    // start lock the lockKey
                    boolean tryLockResult = lockService.tryLock(lockKey, lockValue);
//                    boolean tryLockResult = true;
//                    lockService.lock(lockKey, lockValue);
                    // get the most new lock info with lockKey
                    String currentLockValue= lockService.getCurrentLockValue(lockKey);
                    System.out.println("[LockThread]Thread[" + Thread.currentThread().getId() + "] lock result:" + tryLockResult + ",current lock info:" + currentLockValue);
                    // here do some business opearation
                    try {
                        TimeUnit.SECONDS.sleep((int) (Math.random() * 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // continue to fight for lock if failed
                    if(!tryLockResult){
                        continue;
                    }
                    // start unlock the lockKey with lockKey & lockValue
                    lockService.unlock(lockKey, lockValue);
                    try {
                        TimeUnit.SECONDS.sleep((int) (Math.random() * 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
