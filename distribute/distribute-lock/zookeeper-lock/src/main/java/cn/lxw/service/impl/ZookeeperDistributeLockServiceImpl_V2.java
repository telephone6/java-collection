package cn.lxw.service.impl;

import cn.lxw.constant.ZkLockEnvironment;
import cn.lxw.service.ILockService;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("zkLock_V2")
public class ZookeeperDistributeLockServiceImpl_V2 implements ILockService {

    @Resource
    private ZooKeeper zooKeeper;

    @Override
    public void lock(String lockKey, String lockValue) {
        String baseLockPath = ZkLockEnvironment.BASE_LOCK_PATH;
        Watcher watcher = ZkLockEnvironment.ZK_LOCK_WATCHER;
        boolean exceptionOccurred = false;
        String lockPath = null;

        try {
            lockPath = zooKeeper.create(baseLockPath + "/" + lockKey,
                    lockValue.getBytes(ZkLockEnvironment.DEFAULT_CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取所有锁信息
            List<String> lockPathList = zooKeeper.getChildren(baseLockPath, false);
            Collections.sort(lockPathList);
            int index = lockPathList.indexOf(lockPath.substring(baseLockPath.length() + 1));
            // 如果lockPath是序号最小的节点，则获取锁
            if (index > 0) {
                // lockPath不是序号最小的节点，监听前一个节点
                String preLockPath = lockPathList.get(index - 1);
                while (zooKeeper.exists(baseLockPath + "/" + preLockPath, watcher) != null) {
                    synchronized (watcher) {
                        watcher.wait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            exceptionOccurred = true;
            throw new RuntimeException(e.getMessage());
        }finally {
            if(exceptionOccurred && lockPath != null){
                try {
                    zooKeeper.delete(lockPath, -1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean tryLock(String lockKey, String lockValue) {
        String baseLockPath = ZkLockEnvironment.BASE_LOCK_PATH;

        try {
            String lockPath = zooKeeper.create(baseLockPath + "/" + lockKey,
                    lockValue.getBytes(ZkLockEnvironment.DEFAULT_CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取所有锁信息
            List<String> lockPathList = zooKeeper.getChildren(baseLockPath, false);
            Collections.sort(lockPathList);
            int index = lockPathList.indexOf(lockPath.substring(baseLockPath.length() + 1));
            // 如果lockPath是序号最小的节点，则获取锁
            if (index > 0) {
                zooKeeper.delete(lockPath, -1);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(String lockKey, String lockValue, long expireTime, TimeUnit unit) {
        return false;
    }

    @Override
    public boolean unlock(String lockKey, String lockValue) {
        String baseLockPath = ZkLockEnvironment.BASE_LOCK_PATH;
        try {
            String currentLockKey = getCurrentLockKey(lockKey);
            if(lockValue.equals(getCurrentLockValue(lockKey))){
                zooKeeper.delete(baseLockPath + "/" + currentLockKey, -1);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getCurrentLockKey(String lockKey) {String baseLockPath = ZkLockEnvironment.BASE_LOCK_PATH;
        List<String> lockPathList = null;
        try {
            lockPathList = zooKeeper.getChildren(baseLockPath, false);
            Collections.sort(lockPathList);
            if(lockPathList.size() > 0){
                return lockPathList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCurrentLockValue(String lockKey) {
        String baseLockPath = ZkLockEnvironment.BASE_LOCK_PATH;
        List<String> lockPathList = null;
        try {
            lockPathList = zooKeeper.getChildren(baseLockPath, false);
            Collections.sort(lockPathList);
            if(lockPathList.size() > 0){
                byte[] data = zooKeeper.getData(baseLockPath + "/" + lockPathList.get(0), null, null);
                return new String(data,ZkLockEnvironment.DEFAULT_CHARSET);
            }
        } catch (Exception e) {

        }
        return null;
    }
}
