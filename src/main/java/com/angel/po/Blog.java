package com.angel.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: Type
 * @Package: com.angel.po.Type
 * @Description: 博客的实体类 : pojo
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 20:30
 */
@Getter
@Setter
@ToString
@Entity                 // 这是JPA的注解，具备和数据表字段对应的能力
@Table(name = "t_blog") // 对应的表的，如果不自己起表名，那么系统会以当前的pojo实体类的类名作为表名
public class Blog {

    @Id                 // 代表的是id是主键
    @GeneratedValue     // 指的生成策略(这里使用的是默认的生成策略->MySQL自动生成id策略)
    private Long id;                        // 代表主键
    private String title;                   // 标题
    @Basic(fetch = FetchType.LAZY)
    @Lob            // 这是一个大字段类型，只有当我们初始化的时候才有效，通常和Basic进行懒加载。使用的时候加加载，不使用的时候就不加载，对应到数据库的类型是longtext
    private String content;                 // 博客内容
    private String firstPicture;            // 博客对应的首图
    private String flag;                    // 标记 : 是否原创、转载
    private Integer views = 0;                  // 浏览次数
    private boolean appreciation;           // 赞赏是否开启
    private boolean shareStatement;         // 转载声明是否开启
    private boolean commentabled;           // 评论是否开启
    private boolean published;              // 发布是否开启，不发布就是保存为草稿
    private boolean recommend;              // 推荐是否开启

    @Temporal(TemporalType.TIMESTAMP)       // 生成对应到数据库中的时间，需要注解 TemporalType.TIMESTAMP：指的是数据库时间类型，这时是日期 + 时间
    private Date createTime;                // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;                // 修改时间
    // private String createUser;              // 创建人

    /**
     * Blog 和 Type 的关系 是 多对一的关系
     */
    @ManyToOne              // 表示多对一， 这里是多的一端
    private Type type;          // 一个博客只能有一个Type，也就是只能有一个类型(但是多个博客可以对应一个类型，所以是多对一)

    /**
     * Blog 和 Tag 的关系 是 多对多的关系
     * 设置 Blog 和 Tag 的级联关系
     * 需求：当新增一个博客的时候，需要新增联同新增一个新的标签也新增到数据库中，就需要级联新增。
     * (cascade = {CascadeType.PERSIST}) : 级联更新。
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    /**
     * Blog 和 User 的关系 是 多对一的关系 : 也就是说多个博客可以对应一个用户，Blog是多的一端。一个博客只能是一个用户新增
     * @ManyToOne 这里是关系的维护方
     */
    @ManyToOne      // 多个博客对应一个用户
    private User user;


    /**
     * Blog 和 Comment 的关系 是 一对多的关系
     * Blog-->Comment : 一个博客可以对应多条留言    @OneToMany
     * Comment-->Blog : 多条留言只能对应一个博客    @ManyToOne
     *(mappedBy = "blog" :  这里的 "blog" 是 Comment 类下的 Blog blog 属性)
     */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();



    @Transient          // 不会进行数据库的操作，不和数据的映射
    private String tagIds;

    private String description;

    // 必须要有的空构造
    public Blog() {

    }

}
