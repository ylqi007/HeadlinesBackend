

## Logging Level
* 在try-catch中，`e.printStackTrace();`就不要再用了。会打印出堆栈日志
* 使用`{}`占位符，而不是字符串拼接。`{}`性能更高
* 判断debug模式是否开启
* DEBUG，开发、测试、联调的时候
* logging level=INFO时，会打印INFO及以上level，即INFO，WARN，ERROR，FATAL，而DEBUG是不会被打印的
* 使用Slf4j，门面，而不是实际的底层实现Log4j2
* Youtube: [JAVA程序员如何正确打日志 日志框架选择,日志级别,日志实操,MDC源码解析](https://www.youtube.com/watch?v=8iq4JNKWXaY)