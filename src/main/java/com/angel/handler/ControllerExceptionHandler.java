package com.angel.handler;

import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义拦截异常，这里是拦截所有带有@Controller的请求，哪些是可以返回错误的页面，拦截所有的异常，进行统一的管理，做一些逻辑判断
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    // 将错误信息记录到日志中
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        logger.error("自定义日志 : ###============### 异常-> Request URL : {} , Exception : {}",request.getRequestURL(),e);
        /*通过注解工具，判断ResponseStatus是否有设置状态，如果有，则抛出异常,让SpringBoot进行处理*/
        /*这里是进行了排除*/
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");      // 跳转到自定义的异常页面
        return mv;

    }

}
