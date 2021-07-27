package cn.lxw;

import cn.lxw.entity.DistributeLockInfo;
import cn.lxw.service.ILockService;
import cn.lxw.util.LockInfoUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.lxw")
@EnableScheduling
public class MainApp {

    /**
     * 功能描述: <br>
     * 〈DistributeLock testing start here!〉
     *
     * @Param: [args]
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/7/26 18:20
     */
    public static void main(String[] args) {
        // run the springboot app
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class, args);
        // define some lock infomation
        final String lockKey = "lock_test";
        ILockService lockService = context.getBean(ILockService.class);
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
                    boolean tryLockResult = lockService.tryLock(lockKey, lockValue, 10L, TimeUnit.SECONDS);
                    // get the most new lock info with lockKey
                    DistributeLockInfo currentLockInfo = lockService.getLock(lockKey);
                    System.out.println("[LockThread]Thread[" + Thread.currentThread().getId() + "] lock result:" + tryLockResult + ",current lock info:" + (currentLockInfo==null?"null":currentLockInfo.toString()));
                    // here do some business opearation
//                    try {
//                        TimeUnit.SECONDS.sleep((int) (Math.random() * 10));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    // continue to fight for lock if failed
                    if(!tryLockResult){
                        continue;
                    }
                    // start unlock the lockKey with lockKey & lockValue
                    lockService.unlock(lockKey, lockValue);
                }
            });
        }
    }
}
