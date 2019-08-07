package com.angel.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: User
 * @Package: com.angel.po.User
 * @Description: 用户的实体类 : pojo
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 21:05
 */

@Getter
@Setter
@ToString
@Entity                 // 这是JPA的注解，具备和数据表字段对应的能力
@Table(name = "t_user") // 对应的表的，如果不自己起表名，那么系统会以当前的pojo实体类的类名作为表名
public class User {

    @Id                 // 代表的是id是主键
    @GeneratedValue     // 指的生成策略(这里使用的是默认的生成策略->MySQL自动生成id策略)
    private Long id;
    private String nickname;        // 昵称
    private String username;        // 用户名
    private String password;        // 密码
    private String email;           // 邮箱
    private String avatar;          // 头像
    private Integer type;           // 用户类型: 是管理员，还是普通成员

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;        // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;        // 更新时间

    /**
     * Blog: 是维护端
     * User: 这里是被维护端
     * 一对多的关系 (mappedBy = "user" :  这里的"user"是Blog类下的 User user 属性)
     */
    @OneToMany(mappedBy = "user")              // 一对多  一个用户可以写多个博客
    private List<Blog> blogs = new ArrayList<>();

    public User() {

    }
}
