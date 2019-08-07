package com.angel.service;

import com.angel.NotFoundException;
import com.angel.dao.TypeRepository;
import com.angel.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Title: TypeServiceImpl
 * @Package: com.angel.service.TypeServiceImpl
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 03:50
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;


    // 保存功能
    // @Transactional : 针对增、删、改、查的操作，需要放在事务里面。
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    // 根据 id 查询一条记录
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }


    // 根据 Name 查询一条记录
    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    // 分页查询
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    // 根据 id 更新 一条记录
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该分类的类型，无法完成修改操作");
        }
        BeanUtils.copyProperties(type, t);   // 把 type里面的值赋值到 t 对象中。

        return typeRepository.save(t);      // 因为 t 中有 id ，所以会完成修改的一个操作。
    }

    // 根据 id 删除 一行数据
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
