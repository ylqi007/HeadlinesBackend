package com.atguigu.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 9/20/24 20:00
 */
@Slf4j
public class LoggerTest {
    @Test
    public void testLogLevel() {
        log.error("ERROR: 这个错误很严重，程序阻断了，要记录监控，发到警报");
        log.warn("WARN: 这个错误不应该出来啊，程序可以继续往下走，但是太没面子了");
        log.info("INFO: 没啥事儿，就是想打个日志");
        log.debug("DEBUG: 作为一个经常写BUG的程序员，开发联调阶段，多打点日志很合理");
        log.trace("TRACE: 从来没有用过，他们说是为了追踪！");

        // 正确打印debug
        if(log.isDebugEnabled()) {
            log.debug("DEBUG: 别嫌麻烦，不想被开除，最好这样做！");
        }

        MDC.put("TraceId", "Xxxxx12345");
    }
}
