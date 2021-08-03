package cn.lxw.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 * @ClassName: cn.lxw.debug.DebugLogger.java
 * @Description:
 *
 * @Author: luoxw
 * @Date: 2021/8/3 19:12
 */
public class DebugLogger {
    private static final Logger logger = LoggerFactory.getLogger(DebugLogger.class);

    /**
     * 功能描述: <br>
     * 〈Print debug-level log〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:12
     */
    public static void printLog(){
        logger.debug("print debug log");
    }

}
