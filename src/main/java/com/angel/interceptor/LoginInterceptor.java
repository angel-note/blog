package com.angel.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: LoginInterceptor
 * @Package: com.angel.interceptor.LoginInterceptor
 * @Description: 所有页面的过滤器，在没有登录之前是不能任意访问界面，只有登录成功才能访问
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 03:01
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 在第个请求之前，需要先进行操作。进行请求路径以及权限的过滤
     * 下面的实现只是实现了所有的请求路径，这样是不行的。我们只过滤 /admin路径请求下的页面。其他页面请求不作过滤，所以需要再次配置
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getSession().getAttribute("user") == null){      // 没有 user对象，则表明未登录
            response.sendRedirect("/admin");        // 那么，需要重定向到/admin这个请求路径下，也就是会进行登录界面
            return false;
        }
        return true;        // 如果有user对象，则继续往下执行
    }
}
