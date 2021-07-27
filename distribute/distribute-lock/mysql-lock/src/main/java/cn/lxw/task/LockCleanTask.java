package cn.lxw.task;

import cn.lxw.configdb.DistributeLockInfoMapper;
import cn.lxw.entity.DistributeLockInfo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class LockCleanTask {

    @Resource
    private DistributeLockInfoMapper lockInfoMapper;

    /**
     * 功能描述: <br>
     * 〈Clean the lock which is expired.〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/7/26 20:13
     */
    @Scheduled(cron = "0/6 * * * * *")
    public void cleanExpireLock() {
        int deleteResult = lockInfoMapper.delete(new UpdateWrapper<DistributeLockInfo>() {
            {
                le("expire_time", LocalDateTime.now());
            }
        });
        System.out.println("[LockCleanTask]The count of expired lock is " + deleteResult + "!");
    }
}
