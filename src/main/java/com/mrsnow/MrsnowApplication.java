package com.mrsnow;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.mrsnow"})
@MapperScan("com.mrsnow.web.mapper*")
@EnableTransactionManagement
@Slf4j
public class MrsnowApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MrsnowApplication.class, args);

        Environment env = applicationContext.getEnvironment();

        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 启动成功! 访问连接:\n\t" +
                        "服务端: \t\thttp://{}:{}\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port", "8080"));
    }

}
