package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     *
     * @param dto
     */
    public void addCategory(CategoryDTO dto) {
        // 创建一个Category对象，用于封装数据
        Category category = new Category();

        // 将 dto 对象属性拷贝给 category 对象
        BeanUtils.copyProperties(dto, category);

        // 补全其他属性
        category.setStatus(StatusConstant.ENABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        // 从上下文（threadLocal）获取当前登录用户的 id
        Long currUserId = BaseContext.getCurrentId();
        category.setCreateUser(currUserId);
        category.setUpdateUser(currUserId);

        // 插入数据库
        categoryMapper.add(category);
    }
}
