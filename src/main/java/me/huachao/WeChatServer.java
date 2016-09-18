package me.huachao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by huachao on 1/22/16.
 */
@SpringBootApplication
public class WeChatServer {

    private static final Logger logger = LoggerFactory.getLogger(WeChatServer.class);

    public static void main(String[] args) {
        SpringApplication.run(WeChatServer.class, args);
        logger.info("++++++++++++++++++++ wechat server started ++++++++++++++++++++");
    }

}
