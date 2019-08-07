package com.angel.po;

/*类型的实体类：pojo*/

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Type
 * @Package: com.angel.po.Type
 * @Description: 分类的实体类 : pojo
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 20:30
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_type")
public class Type {

    @Id                 // 代表的是id是主键
    @GeneratedValue     // 指的生成策略(这里使用的是默认的生成策略->MySQL自动生成id策略)
    private Long id;            // 主键
    @NotBlank(message = "分类名称不能为空")     // 前端做了非空校验，但是很容易绕过去，所以后端也需要做非空校验，如果保存拿到一个空值，则会给出一个错误的提示。同时也会在后台上抛出一个异常
    private String name;        // 分类的名字

    /**
     * Blog: 是维护端
     * Type: 这里是被维护端
     * 一对多的关系 (mappedBy = "type" :  这里的"type"是Blog类下的Type type属性)
     */
    @OneToMany(mappedBy = "type")
    private List<Blog> bogs = new ArrayList<>();        // 一个类型对应多个Blog,所以这里要定义一个Blog的集合

    public Type() {

    }
}
