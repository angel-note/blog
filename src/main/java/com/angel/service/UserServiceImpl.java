package com.angel.service;

import com.angel.dao.UserRepository;
import com.angel.po.User;
import com.angel.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: UserServiceImpl
 * @Package: com.angel.service.UserServiceImpl
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 23:33
 */
@Service        // 说明这是一个Service层
public class UserServiceImpl implements UserService {

    // 要使用dao操作数据库，则需要注入dao的实现。这里是对User进行操作，所以注入User的dao实现
    @Autowired
    private UserRepository userRepository;
    /**
     * 需求：从前端登录页面拿到用户名和密码后进行登录，但是在登录前需要检查一下数据库是否存在该用户，
     *      如果存在，则返回一个User对象，如果不存在，则返回一个null空对象。
     * @param username : 用户名
     * @param password : 密码
     * @return  : 返回的结果值
     */
    @Override
    public User checkUser(String username, String password) {
        // 操作数据库
        // MD5Utils.code(password) : 使用MD5进行加密，然后和数据库数据进行比较
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));

        return user;
    }
}
