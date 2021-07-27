package cn.lxw.frame;

import cn.lxw.frame.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MainApp {

    /**
     * 功能描述: <br>
     * 〈JTA XA transaction start〉
     * @Param: [args]
     * @Return: {@link Void}
     * @Author: luoxw
     * @Date: 2021/7/22 17:20
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MainApp.class, args);
        run.getBean(TransactionService.class).testTransaction();
    }
}
