package cn.lxw.service;

import java.util.concurrent.TimeUnit;

public interface ILockService {

    /**
     * 功能描述: <br>
     * 〈Lock until success!〉
     * @Param: [lockKey, lockValue]
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    void lock(String lockKey, String lockValue);

    /**
     * 功能描述: <br>
     * 〈Lock method, return the result immediately if failed .〉
     * @Param: [lockKey, lockValue]
     * @Return: {@link boolean}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    boolean tryLock(String lockKey, String lockValue);

    /**
     * 功能描述: <br>
     * 〈Lock with a timeout param, return the result immediately if failed.If lock success and it is expired,will be freed by LockCleanTask〉
     * @Param: [lockKey, lockValue, expireTime, unit]
     * @Return: {@link boolean}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    boolean tryLock(String lockKey, String lockValue, long expireTime, TimeUnit unit);

    /**
     * 功能描述: <br>
     * 〈Unlock with lockKey & lockValue.If doesn't matched,will be lock failed.〉
     * @Param: [lockKey, lockValue]
     * @Return: {@link boolean}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    boolean unlock(String lockKey, String lockValue);


    /**
     * 功能描述: <br>
     * 〈Get lock key by lockKey!〉
     * @Param: [lockKey]
     * @Return: {@link String}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    String getCurrentLockKey(String lockKey);

    /**
     * 功能描述: <br>
     * 〈Get lock value by lockKey!〉
     * @Param: [lockKey]
     * @Return: {@link String}
     * @Author: luoxw
     * @Date: 2021/7/26 20:14
     */
    String getCurrentLockValue(String lockKey);
}
