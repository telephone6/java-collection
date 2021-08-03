package cn.lxw.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 * @ClassName: cn.lxw.error.ErrorLogger.java
 * @Description:
 *
 * @Author: luoxw
 * @Date: 2021/8/3 19:13
 */
public class ErrorLogger {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogger.class);

    /**
     * 功能描述: <br>
     * 〈Print error-level log〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:12
     */
    public static void printLog(){
        logger.error("print error log");
    }

}
