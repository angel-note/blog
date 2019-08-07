package com.angel.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Title: BlogQuery
 * @Package: com.angel.vo.BlogQuery
 * @Description: 针对博客列表的三个条件，单独定义一个对象
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-06 23:12
 */
@Setter
@Getter
@ToString
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogQuery() {
    }
}
