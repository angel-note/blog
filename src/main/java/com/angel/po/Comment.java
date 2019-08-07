package com.angel.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Title: Comment
 * @Package: com.angel.po.Comment
 * @Description: 评论留言的实体类 : pojo
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-03 20:56
 */

@Getter
@Setter
@ToString
@Entity                 // 这是JPA的注解，具备和数据表字段对应的能力
@Table(name = "t_comment") // 对应的表的，如果不自己起表名，那么系统会以当前的pojo实体类的类名作为表名
public class Comment {

    @Id                 // 代表的是id是主键
    @GeneratedValue     // 指的生成策略(这里使用的是默认的生成策略->MySQL自动生成id策略)
    private Long id;
    private String nickname;            // 昵称
    private String email;               // 邮箱
    private String content;             // 评论的内容
    private String avatar;              // 头像，是图片的链接地址

    @Temporal(TemporalType.TIMESTAMP)       // 生成对应到数据库中的时间，需要注解 TemporalType.TIMESTAMP：指的是数据库时间类型，这时是日期 + 时间
    private Date createTime;            // 创建的时间

    /**
     * Comment 和 Blog 的关系是 : 多对一的关系
     * Comment-->Blog : 多条留言只能对应一个博客    @ManyToOne
     *  多的一端是关系的维护方
     *  一的一端是关系的被维护方
     *  所以要在Blog 一的一端建立关系关系
     *
     */
    @ManyToOne
    private Blog blog;

    /**
     * 自关联关系：评论与评论的相互对象的关系
     * 一个父对象可能包括多个子对象  : 一个留言可以会有多条回复留言
     */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyCommentList;

    @ManyToOne
    private Comment parentComment;
    private boolean adminComment;

    public Comment() {

    }
}
