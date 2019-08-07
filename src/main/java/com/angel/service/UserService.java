package com.angel.service;

import com.angel.po.User;

/**
 * @Title: UserService
 * @Package: com.angel.service.UserService
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 23:32
 */
public interface UserService {

    /*根据传入的用户名和密码进行检查用户名和密码*/
    User checkUser(String username,String password);
}
