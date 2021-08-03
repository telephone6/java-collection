package cn.lxw.util;

/**
 * 功能描述: <br>
 * 〈A string util of lock.〉
 * @Param:
 * @Return: {@link }
 * @Author: luoxw
 * @Date: 2021/7/26 20:09
 */
public class LockInfoUtil {

    private static final String LOCAL_IP = "192.168.8.8";
    private static final String NODE_ID = "node1";
    private static final String STR_SPILT = "-";
    private static final String STR_LEFT = "[";
    private static final String STR_RIGHT = "]";
    
    /**
     * 功能描述: <br>
     * 〈Return the unique String value of lock info.〉
     * "[192.168.8.8]-[node1]-[37]-[pool-1-thread-1]-[1627301265325]"
     * @Param: []
     * @Return: {@link String}
     * @Author: luoxw
     * @Date: 2021/7/26 20:08
     */
    public static String createLockValue(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(STR_LEFT)
                .append(LOCAL_IP)
                .append(STR_RIGHT)
                .append(STR_SPILT)
                .append(STR_LEFT)
                .append(NODE_ID)
                .append(STR_RIGHT)
                .append(STR_SPILT)
                .append(STR_LEFT)
                .append(Thread.currentThread().getId())
                .append(STR_RIGHT)
                .append(STR_SPILT)
                .append(STR_LEFT)
                .append(Thread.currentThread().getName())
                .append(STR_RIGHT)
                .append(STR_SPILT)
                .append(STR_LEFT)
                .append(System.currentTimeMillis())
                .append(STR_RIGHT);
        return stringBuilder.toString();
    }
}
