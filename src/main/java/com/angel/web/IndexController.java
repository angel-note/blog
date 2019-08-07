package com.angel.web;

import com.angel.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/blog-detail")
    public String blogDetail() {
        return "blog-detail";
    }

    // 通用的测试页面重定向
    // PathVariable : 拿到请求路径中的参数
    @GetMapping("/{pageName}")
    public String PageRedirection(@PathVariable String pageName) {
        return pageName;
    }

}
