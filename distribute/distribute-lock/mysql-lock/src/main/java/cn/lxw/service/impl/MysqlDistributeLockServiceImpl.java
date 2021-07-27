package cn.lxw.service.impl;

import cn.lxw.configdb.DistributeLockInfoMapper;
import cn.lxw.entity.DistributeLockInfo;
import cn.lxw.service.ILockService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Primary
public class MysqlDistributeLockServiceImpl implements ILockService {

    @Resource
    private DistributeLockInfoMapper lockInfoMapper;

    /**
     * 功能描述: <br>
     * 〈Lock until success!〉
     * @Param: [lockKey, lockValue]
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    @Override
    public void lock(String lockKey, String lockValue) {
        int insertResult = 0;
        // trying until success
        while(insertResult < 1) {
            insertResult = lockInfoMapper.insertIgnore(new DistributeLockInfo() {
                {
                    setLockKey(lockKey);
                    setLockValue(lockValue);
                }
            });
        }
    }

    /**
     * 功能描述: <br>
     * 〈Lock method, return the result immediately if failed .〉
     * @Param: [lockKey, lockValue]
     * @Return: {@link boolean}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    @Override
    public boolean tryLock(String lockKey, String lockValue) {
        int insertResult = lockInfoMapper.insertIgnore(new DistributeLockInfo() {
            {
                setLockKey(lockKey);
                setLockValue(lockValue);
            }
        });
        return insertResult == 1;
    }

    /**
     * 功能描述: <br>
     * 〈Lock with a timeout param, return the result immediately if failed.If lock success and it is expired,will be freed by LockCleanTask {@link cn.lxw.task.LockCleanTask}〉
     * @Param: [lockKey, lockValue, expireTime, unit]
     * @Return: {@link boolean}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    @Override
    public boolean tryLock(String lockKey, String lockValue, long expireTime, TimeUnit unit) {
        long expireNanos = unit.toNanos(expireTime);
        LocalDateTime expireDateTime = LocalDateTime.now().plusNanos(expireNanos);
        int insertResult = lockInfoMapper.insertIgnore(new DistributeLockInfo() {
            {
                setLockKey(lockKey);
                setLockValue(lockValue);
                setExpireTime(expireDateTime);
            }
        });
        return insertResult == 1;
    }

    /**
     * 功能描述: <br>
     * 〈Unlock with lockKey & lockValue.If doesn't matched,will be lock failed.〉
     * @Param: [lockKey, lockValue]
     * @Return: {@link boolean}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    @Override
    public boolean unlock(String lockKey, String lockValue) {
        int deleteResult = lockInfoMapper.delete(new UpdateWrapper<DistributeLockInfo>() {
            {
                eq("lock_key", lockKey);
                eq("lock_value", lockValue);

            }
        });
        return deleteResult == 1;
    }

    /**
     * 功能描述: <br>
     * 〈Get lock info by lockKey!〉
     * @Param: [lockKey]
     * @Return: {@link DistributeLockInfo}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    @Override
    public DistributeLockInfo getLock(String lockKey) {
        return lockInfoMapper.selectOne(new QueryWrapper<DistributeLockInfo>(){
            {
                eq("lock_key",lockKey);
            }
        });
    }
}
