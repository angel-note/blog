package com.angel.dao;

import com.angel.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Title: BlogRepository
 * @Package: com.angel.dao.BlogRepository
 * @Description:
 *      JpaSpecificationExecutor : 帮助我们实现动态组合查询
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-06 21:30
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

}
