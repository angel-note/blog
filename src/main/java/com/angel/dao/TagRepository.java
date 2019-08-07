package com.angel.dao;

import com.angel.po.Tag;
import com.angel.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: TagRepository
 * @Package: com.angel.dao.TagRepository
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-05 01:13
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    // 根据 Name查询一条记录
    Tag findByName(String name);       // 因为jpa 的JpaRepository 没有这个方法，所以需要自定义
}
