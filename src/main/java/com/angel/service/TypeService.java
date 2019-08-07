package com.angel.service;

import com.angel.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Title: TypeService
 * @Package: com.angel.service.TypeService
 * @Description: Service层的标签接口->增删改查分页方法定义
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 03:45
 */
public interface TypeService {

    Type saveType(Type type);                   // 新增标签

    Type getType(Long id);                      // 根据 Long 类型的 id 查询标签结果

    Type getTypeByName(String name);                  // 根据 String类型的名查询 Type 结果

    Page<Type> listType(Pageable pageable);     // 分页查询

    List<Type> listType();              // 仅仅是查询所有的数据

    Type updateType(Long id, Type type);        // 根据 id 修改 Type

    void deleteType(Long id);                   // 根据主键id删除标签

}
