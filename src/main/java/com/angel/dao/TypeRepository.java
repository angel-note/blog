package com.angel.dao;

import com.angel.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: TypeRepository
 * @Package: com.angel.dao.TypeRepository
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 03:51
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    // 根据 Name查询一条记录
    Type findByName(String name);       // 因为jpa 的JpaRepository 没有这个方法，所以需要自定义


}
