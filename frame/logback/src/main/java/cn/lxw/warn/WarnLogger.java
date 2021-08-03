package cn.lxw.warn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 * @ClassName: cn.lxw.warn.WarnLogger.java
 * @Description:
 *
 * @Author: luoxw
 * @Date: 2021/8/3 19:13
 */
public class WarnLogger {
    private static final Logger logger = LoggerFactory.getLogger(WarnLogger.class);

    /**
     * 功能描述: <br>
     * 〈Print warn-level log〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:12
     */
    public static void printLog(){
        if(logger.isWarnEnabled()) {
            logger.warn("print warn log");
        }
    }

}
