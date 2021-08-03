package cn.lxw.constant;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class ZkLockEnvironment {

    public static final String BASE_LOCK_PATH = "/distribute_lock";

    public static String ZK_SERVER_ADDR = "localhost:2181";

    public static Integer SESSION_TIMEOUT = 6000;

    public static final String DEFAULT_CHARSET = "utf-8";


    public static final Watcher ZK_LOCK_WATCHER = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            synchronized (this) {
                notifyAll();
            }
        }
    };
}
