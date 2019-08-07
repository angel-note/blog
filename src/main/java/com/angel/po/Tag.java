package com.angel.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Tag
 * @Package: com.angel.po.Tag
 * @Description: 标签的实体类 : pojo
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 20:52
 */
@Getter
@Setter
@ToString
@Entity                 // 这是JPA的注解，具备和数据表字段对应的能力
@Table(name = "t_tag") // 对应的表的，如果不自己起表名，那么系统会以当前的pojo实体类的类名作为表名
public class Tag {

    @Id                 // 代表的是id是主键
    @GeneratedValue     // 指的生成策略(这里使用的是默认的生成策略->MySQL自动生成id策略)
    private Long id;            // 主键
    @NotBlank(message = "标签名称不能为空")     // 前端做了非空校验，但是很容易绕过去，所以后端也需要做非空校验，如果保存拿到一个空值，则会给出一个错误的提示。同时也会在后台上抛出一个异常
    private String name;        // 标签的名字

    /**
     * Blog: 是维护端
     * Tag: 这里是被维护端 ,所以要建立mappedBy的映射关系
     * 多对多的关系 (mappedBy = "tags" :  这里的"tags"是Blog类下的Tag tags 属性)
     */
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {

    }
}
