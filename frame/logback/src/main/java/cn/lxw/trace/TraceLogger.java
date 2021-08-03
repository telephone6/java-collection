package cn.lxw.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 * @ClassName: cn.lxw.trace.TraceLogger.java
 * @Description:
 *
 * @Author: luoxw
 * @Date: 2021/8/3 19:13
 */
public class TraceLogger {
    private static final Logger logger = LoggerFactory.getLogger(TraceLogger.class);

    /**
     * 功能描述: <br>
     * 〈Print trace-level log〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:12
     */
    public static void printLog(){
        logger.trace("print trace log");
    }

}
