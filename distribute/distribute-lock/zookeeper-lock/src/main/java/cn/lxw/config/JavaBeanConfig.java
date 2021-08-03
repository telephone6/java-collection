package cn.lxw.config;

import cn.lxw.constant.ZkLockEnvironment;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JavaBeanConfig {
    @Bean
    public ZooKeeper zooKeeper(){
        try {
            // get client
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 60000, ZkLockEnvironment.ZK_LOCK_WATCHER);
            // init base path
            Stat lockBasePathExist = zooKeeper.exists(ZkLockEnvironment.BASE_LOCK_PATH, false);
            if(lockBasePathExist == null) {
                zooKeeper.create(ZkLockEnvironment.BASE_LOCK_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }
            return zooKeeper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
