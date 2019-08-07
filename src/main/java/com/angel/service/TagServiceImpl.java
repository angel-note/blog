package com.angel.service;

import com.angel.NotFoundException;
import com.angel.dao.TagRepository;
import com.angel.dao.TagRepository;
import com.angel.po.Tag;
import com.angel.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: TagServiceImpl
 * @Package: com.angel.service.TagServiceImpl
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-05 01:18
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    // 保存功能
    // @Transactional : 针对增、删、改、查的操作，需要放在事务里面。
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // 根据 id 查询一条记录
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }


    // 根据 Name 查询一条记录
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    // 分页查询
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {  // ids = "1,2,3"
        // 需要把字符串转换成一个iterator的集合
        return tagRepository.findAllById(covertToList(ids));
    }

    // 需要把字符串转换成一个iterator的集合
    private List<Long> covertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (ids != null && !"".equals(ids)){
            String[] idArray = ids.split(",");
            for (int i = 0 ;i<idArray.length; i ++){
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }


    // 根据 id 更新 一条记录
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签的类型，无法完成修改操作");
        }
        BeanUtils.copyProperties(tag, t);   // 把 tag里面的值赋值到 t 对象中。

        return tagRepository.save(t);      // 因为 t 中有 id ，所以会完成修改的一个操作。
    }

    // 根据 id 删除 一行数据
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
