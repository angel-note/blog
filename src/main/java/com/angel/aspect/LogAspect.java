package com.angel.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面，需要记录以下日志
 * - 请求 url
 * - 访问者 ip
 * - 调用方法 classMethod
 * - 参数 args
 * - 返回内容
 */
@Aspect
@Component
public class LogAspect {

    // 拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    Pointcut : 把这个方法定义成一个切面，需要拦截，不管什么请求，只要是com.angel.web包下的，都会进入这个切面，进行日志的处理
    execution(* com.angel.web.*.*(..):
        第一个 * : 不管是public ，private, protected。
        第二个com.angel.web.*.*(..): 指的是com.angel.web包下的所有的类*,下的所有的方法*(..)
     */
    /*定义一个切面*/
    @Pointcut("execution(* com.angel.web.*.*(..))")
    public void log(){
        logger.info("自定义日志 : ###============### -> 这是一个切面");

    }

    // 在切面之前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        /*如何获取HttpRequest*/
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        // 拿到类名和方法名，进行组合
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() +"."+joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();    // 拿到请求的参数
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);


        logger.info("自定义日志 : ###============### -> requestLog : {}",requestLog);


    }

    // 在切面之后执行
    @After("log()")
    public void doAfter(){
//        logger.info("自定义日志 : ###============### -> 在切面执行之后执行");

    }

    /*
        执行完方法，返回的之后进行拦截，需要记录一些返回的值,捕获到各个方法返回的内容
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("自定义日志 : ###============### -> Result : {}",result);

    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
