package cn.lxw.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 * @ClassName: cn.lxw.info.InfoLogger.java
 * @Description:
 *
 * @Author: luoxw
 * @Date: 2021/8/3 19:13
 */
public class InfoLogger {
    private static final Logger logger = LoggerFactory.getLogger(InfoLogger.class);

    /**
     * 功能描述: <br>
     * 〈Print info-level log〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:12
     */
    public static void printLog(){
        logger.info("print info log");
    }

}
