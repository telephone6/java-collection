package cn.lxw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.lxw.mapper")
public class ShardingjdbcdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcdemoApplication.class, args);
    }

}
