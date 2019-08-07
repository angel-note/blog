package com.angel.service;

import com.angel.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Title: TagService
 * @Package: com.angel.service.TagService
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-05 01:14
 */
public interface TagService {

    Tag saveTag(Tag tag);                   // 新增分类

    Tag getTag(Long id);                      // 根据 Long 类型的 id 查询分类结果

    Tag getTagByName(String name);                  // 根据 String类型的名查询 Tag 结果

    Page<Tag> listTag(Pageable pageable);     // 分页查询

    List<Tag> listTag();               // 查询所有的标签数据

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag tag);        // 根据 id 修改 Tag

    void deleteTag(Long id);                   // 根据主键id删除分类

}
