package com.atguigu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 9/18/24 22:50
 */
@SpringBootApplication
@MapperScan("com.atguigu.mapper")   // 在启动类上扫描Mapper类
@Slf4j
@CrossOrigin    // 前端访问，需要跨域
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        SpringApplication.run(Main.class, args);

        log.error("ERROR: 这个错误很严重，程序阻断了，要记录监控，发到警报");
        log.warn("WARN: 这个错误不应该出来啊，程序可以继续往下走，但是太没面子了");
        log.info("INFO: 没啥事儿，就是想打个日志");
        log.debug("DEBUG: 作为一个经常写BUG的程序员，开发联调阶段，多打点日志很合理");
        log.trace("TRACE: 从来没有用过，他们说是为了追踪！");
    }
}