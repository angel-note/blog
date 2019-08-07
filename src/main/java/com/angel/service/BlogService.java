package com.angel.service;

import com.angel.po.Blog;
import com.angel.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Title: BlogService
 * @Package: com.angel.service.BlogService
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-06 21:22
 */
public interface BlogService {

    // 1、根据主键 id 查询一条 Blog 清单记录
    Blog getBlog(Long id);

    // 2、分页查询,联合查询(条件是标题、分类)
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    // 3、新增一条Blog清单记录
    Blog saveBlog(Blog blog);

    // 4、根据 id 修改一条Blog清单记录
    // 首先是根据 id 主键先查出来记录。然后赋值于新的blog清单记录
    Blog updateBlog(Blog blog, Long id);

    // 5、根据 id 删除一条Blog清单记录
    void deleteBlog(Long id);


}
