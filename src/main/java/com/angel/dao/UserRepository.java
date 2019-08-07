package com.angel.dao;

import com.angel.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: UserRepository
 * @Package: com.angel.dao.UserRepository
 * @Description: 使用Spring Data JPA 进行操作数据库
 * JpaRepository<User,Long> : 需要继承这个JpaRepository类，User:指的是操作哪个类，Long:指的是哪个主键的类型
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 23:37
 */
public interface UserRepository extends JpaRepository<User,Long> {
    // JpaRepository 这个类里面有自己的增删改查，所以不用管里面的实现细节。可以直接拿过来使用，也可以自己定义相关的增删改查的方法

    /**
     * 1. 自定义的查询
     * 需要遵循方法名的一定的命名规则 findByUsernameAndPassword
     */
     User findByUsernameAndPassword(String username,String password);

}
