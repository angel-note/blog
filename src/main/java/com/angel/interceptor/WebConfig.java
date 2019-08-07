package com.angel.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Title: WebConfig
 * @Package: com.angel.interceptor.WebConfig
 * @Description: 拦截规则配置类 : 重写关于控制器过滤的一个设置。
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 03:08
 */
@Configuration      // @Configuration : 只有设置这个注解，SpringBoot才会知道这是一个配置类
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         *   new LoginInterceptor() : 把刚才登录的过滤器添加到这里
         *   /admin/** : 指的是admin下的所有的请求路径过滤都需要拦截，并到 LoginInterceptor类进行处理。
         *      但是：这样就会产生一个问题，就是有些 form 表单有 action="/admin/**"的请求都会被拦截掉，就会导致请求不成功。
         *   所以需要排除这些指定的请求路径就需要调用excludePathPatterns()
         */

        registry.addInterceptor(
                new LoginInterceptor())
                    .addPathPatterns("/admin/**")            // 对所有的"/admin/**"的请求进行过滤
                    .excludePathPatterns("/admin")           // 排除 "/admin"这个请求路径
                    .excludePathPatterns("/admin/login");    // 排除 "/admin/login"这个请求路径，这个是点击登录按钮的 action的请求路径
        super.addInterceptors(registry);
    }
}
