package com.angel.service;

import com.angel.NotFoundException;
import com.angel.dao.BlogRepository;
import com.angel.po.Blog;
import com.angel.po.Type;
import com.angel.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: BlogServiceImpl
 * @Package: com.angel.service.BlogServiceImpl
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-06 21:29
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 多种组合条件分页查询
     *  界面上的条件有 : 标题、分类、推荐。这三个条件有不同组合，就会产生不同的查询结果
     * @param pageable
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {

            /**
             *
             * @param root :  把Blog映射成 Root 。 会得到一些表的字段，属性名，可以拿到列名
             * @param criteriaQuery : 查询的一些条件容器
             * @param criteriaBuilder : 设置一些条件的表达式 。 模糊查询：like.等。。。。
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 条件的动态组合就在这里完成
                // 一些个组合条件放入在这个Predicate中
                List<Predicate> predicates = new ArrayList<>();
                // 1、 条件一：判断标题是否为空
                if ( !"".equals(blog.getTitle()) && blog.getTitle() != null){

                    // criteriaBuilder.like : 模糊查询
                    // root.<String>get("title") :  相当于表，拿到表的一个名为"title"的字段,String 表示该字段是String的类型
                    // blog.getTitle() :  从前端传入的值。作为条件
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }

                // 2、条件二：判断是否有选择分类下拉框。
                // 根据 blog中的Type中的id值进行查询判断
                if (blog.getTypeId() !=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }

                // 3、条件三 : 判断是否推荐的多选框是否勾选
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }

                // 进行SQL语句的where条件拼接。
                // 需要把ArrayList  predicates 传入一个数组：predicates.toArray(new Predicate[predicates.size()])
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());     // 新增一条博客，那么创建日期和修改日期也是要初始的
        blog.setUpdateTime(new Date());
        blog.setViews(0);   // 如果新增的方法，那么先给个初始值为 0
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Blog blog, Long id) {
        Blog blog1 = blogRepository.getOne(id);
        if (blog1 == null) {
            throw new NotFoundException("不存在该博客清单，无法完成修改操作");
        }
        BeanUtils.copyProperties(blog, blog1);   // 把 blog里面的值赋值到 blog1 对象中。

        return blogRepository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);

    }
}
