package cn.lxw;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import cn.lxw.debug.DebugLogger;
import cn.lxw.error.ErrorLogger;
import cn.lxw.info.InfoLogger;
import cn.lxw.trace.TraceLogger;
import cn.lxw.warn.WarnLogger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Title:
 * @ClassName: cn.lxw.LogbackMainApp.java
 * @Description:
 *
 * @Author: luoxw
 * @Date: 2021/8/3 19:06
 */
public class LogbackMainApp {

    private static final String LOGGER_PACKAGE = "cn.lxw";

    /**
     * 功能描述: <br>
     * 〈dynamic setting of Logback level start here.〉
     * @Param: [args]
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:06
     */
    public static void main(String[] args) {
        // you should know that the logger-level priority:
        //      trace < debug < info < warn < error

        // At first,define a scheduled thread.
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // Secondly, print log.
            printLog();
            // get logger-package and logger-level here(you can fetch the config from database or config-center)
            // Thirdly, get logger level which is configured by you.
            Level randomLevel = getRandomLevel();
            System.out.println("logger level next time is : " + randomLevel);
            // Finally,refresh the logger level which package is LOGGER_PACKAGE
            refreshLoggerLevel(LOGGER_PACKAGE,randomLevel);
        }, 2, 2, TimeUnit.SECONDS);
    }

    /**
     * 功能描述: <br>
     * 〈print log with different logger levels.〉
     * @Param: []
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:11
     */
    private static void printLog() {
        TraceLogger.printLog();
        DebugLogger.printLog();
        InfoLogger.printLog();
        WarnLogger.printLog();
        ErrorLogger.printLog();
    }

    /**
     * 功能描述: <br>
     * 〈refresh the level setting of the logger context〉
     * @Param: [loggerPackage, loggerLevel]
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/8/3 19:11
     */
    private static void refreshLoggerLevel(String loggerPackage, Level loggerLevel) {
        // #1.get logger context
        ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
        List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
        // #2.filter the Logger object
        List<Logger> packageLoggerList = loggerList.stream().filter(a -> a.getName().startsWith(loggerPackage)).collect(Collectors.toList());
        // #3.set level to logger
        for (ch.qos.logback.classic.Logger logger : packageLoggerList) {
            logger.setLevel(loggerLevel);
        }
    }

    private static Level getRandomLevel(){
        Level[] levelArr = new Level[]{Level.TRACE,Level.DEBUG,Level.INFO,Level.WARN,Level.ERROR};
        int index = (int) (Math.random() * 10) % 6 + 1;
        if(index > levelArr.length - 1){
            index = 0;
        }
        return levelArr[index];
    }
}
