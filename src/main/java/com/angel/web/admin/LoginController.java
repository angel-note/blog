package com.angel.web.admin;

import com.angel.po.User;
import com.angel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Title: LoginController
 * @Package: com.angel.web.admin.LoginController
 * @Description: 登录的Web控制器
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 23:47
 */

@Controller     // 代表一个Web的控制器
// 一个全局的映射路径，这是一个后台请求的全局部分,是在templates/admin。又因为templates是默认就会查找。所这里只要设置admin就可以
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;        // 注入Service的接口对象

    /**
     * 跳转到登录页面
     * 如果没有设置值 ，则会默认使用上面的全局配置的映射请求@RequestMapping("/admin")
     * 当请求路径为/admin，时，则会进入这个方法，显示 admin/admin-login-blog 目录下的登录页面
     *
     * @return
     */
    @GetMapping
    public String loginPage() {
        return "admin/admin-login-blog";
    }

    /**
     * 当点击登录页面上的"登录"按钮，则会以post的方式进行提交。就会执行这个登录login()的方法。
     *
     * @return
     * @RequestParam String username  :  接收页面通过 Post 传递方式的 username 值
     * @RequestParam String password  :  接收页面通过 Post 传递方式的 password 值
     * HttpSession session : 得到前端的session对象
     * RedirectAttributes attributes : 因为登录不成功，使用了重定向的方式返回到登录页面，需要重新登录，则需要使用这个对象进行设值，才能把值传回到前端。不然会拿不到(注意)
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {
        //根据从前端页面传过的username和password去查询数据库是否有对应的值 。
        User user = userService.checkUser(username, password);
        if (user != null) {      // 如果user不为空，则说明是正确的，那么我们则把user对象放到Session对象中。并返回到前端
            user.setPassword(null);     // 不要把密码传回到前端
            session.setAttribute("user", user);
            return "admin/admin-index-blog";  // 返回到登录成功之后的首页面
        } else {
            // 如果不对的话，需要返回到前端，给前端一个提示信息 : 用户名和密码错误
            attributes.addFlashAttribute("message", "用户名和密码错误!");

            // 如果用户名和密码不对，我们则需要返回到后台的登录页面，需要重新登录
            //return "admin/admin-login-blog";    // 不能使用这种方式返回到登录页面，不然的话再次点击登录时会有问题，所以我们采用下面一种。
            return "redirect:/admin"; // 采用重定向的方式返回到登录界面。则会默认调用 loginPage()方法
        }

    }


    /**
     * 注销当前登录的用户
     *     需求：注销用户，则需要清空Session里面所有的值。
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");        // 移除HttpSession中的user对象
        return "redirect:/admin";       // 注销成功，则需要重新返回到登录页面。需要使用重定向。则会默认调用 loginPage()方法

    }
}
